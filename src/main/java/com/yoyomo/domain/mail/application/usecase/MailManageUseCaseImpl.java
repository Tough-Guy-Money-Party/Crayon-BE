package com.yoyomo.domain.mail.application.usecase;

import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.application.domain.service.ApplicationGetService;
import com.yoyomo.domain.mail.application.dto.request.MailReservationRequest;
import com.yoyomo.domain.mail.domain.entity.Mail;
import com.yoyomo.domain.mail.domain.entity.enums.CustomType;
import com.yoyomo.domain.mail.domain.service.MailReserveService;
import com.yoyomo.domain.mail.domain.service.MailSaveService;
import com.yoyomo.domain.mail.exception.DynamodbUploadException;
import com.yoyomo.domain.recruitment.domain.entity.Process;
import com.yoyomo.domain.recruitment.domain.entity.Recruitment;
import com.yoyomo.domain.recruitment.domain.service.ProcessGetService;
import com.yoyomo.domain.template.application.dto.request.MailTemplateUpdateRequest;
import com.yoyomo.domain.template.domain.entity.MailTemplate;
import com.yoyomo.domain.template.domain.service.MailTemplateGetService;
import com.yoyomo.domain.template.domain.service.MailTemplateUpdateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Slf4j
@Service
@RequiredArgsConstructor
public class MailManageUseCaseImpl implements MailManageUseCase {
    private static final int PAGE_SIZE = 100;
    private static final String SOURCE = "mail@crayon.land";

    private final MailSaveService mailSaveService;
    private final MailReserveService mailReservationService;
    private final ApplicationGetService applicationGetService;
    private final ProcessGetService processGetService;
    private final MailTemplateGetService mailTemplateGetService;
    private final MailTemplateUpdateService mailTemplateUpdateService;

    @Override// 예외처리(존재하는 템플릿과 맞는지,
    public void reserve(MailReservationRequest dto) {
//        mailReservationService.create(dto);
        create(dto);
        extract(dto.template());
        UUID templateId = dto.templateId();
        MailTemplate template = mailTemplateGetService.findFromLocal(templateId);
        mailTemplateUpdateService.update(dto.template(), template, templateId);
    }

    private void create(MailReservationRequest dto) {
        long processId = dto.processId();

        Process process = processGetService.find(processId);
        Recruitment recruitment = process.getRecruitment();

        List<CompletableFuture<Void>> uploadFutures = new ArrayList<>();

        process(processId, uploadFutures, dto, recruitment);
        checkUpload(uploadFutures);
    }

    private void process(long processId, List<CompletableFuture<Void>> uploadFutures, MailReservationRequest dto, Recruitment recruitment) {
        Stream.iterate(0, pageNumber -> pageNumber + 1)
                .map(pageNumber -> applicationGetService.findAll(processId, pageNumber, PAGE_SIZE))
                .takeWhile(applications -> !applications.isEmpty())
                .forEach(applications -> uploadApplications(applications, uploadFutures, dto, recruitment));
    }

    private void uploadApplications(List<Application> applications, List<CompletableFuture<Void>> uploadFutures, MailReservationRequest dto, Recruitment recruitment) {
        Set<CustomType> customTypes = extract(dto.template());

        List<Mail> mails = convertToMails(applications, dto, recruitment, customTypes);
        CompletableFuture<Void> uploadFuture = mailSaveService.upload(mails);
        uploadFutures.add(uploadFuture);
    }

    private List<Mail> convertToMails(List<Application> applications, MailReservationRequest dto, Recruitment recruitment, Set<CustomType> customTypes) {
        return applications.stream()
                .map(application -> convert(dto, application, recruitment, customTypes))
                .collect(Collectors.toList());
    }

    private Mail convert(MailReservationRequest dto, Application application, Recruitment recruitment, Set<CustomType> customTypes) {
        Map<String, String> customData = createCustomData(application, recruitment, customTypes);
        String destination = application.getUser().getEmail();

        return dto.toMail(SOURCE, destination , customData);
    }

    private Map<String, String> createCustomData(Application application, Recruitment recruitment, Set<CustomType> customTypes) {
        Map<String, String> dataMap = new HashMap<>();

        for (CustomType customType : customTypes) {
            String value = customType.extractValue(application, recruitment);
            dataMap.put(customType.getPlaceholder(), value);
        }
        return dataMap;
    }

    private Set<CustomType> extract(MailTemplateUpdateRequest dto) {
        String htmlPart = dto.htmlPart();

        // HTML 파싱
        Document doc = Jsoup.parse(htmlPart);

        // data-id 속성이 있는 모든 요소 선택
        Elements elementsWithId = doc.select("[data-id]");
        log.info("추출된 값 {}", elementsWithId);
        Set<CustomType> resultSet = new HashSet<>();

        for (Element element : elementsWithId) {
            String dataId = element.attr("data-id");
            log.info("dataId: {}", dataId);

            // data-id 값과 매칭되는 CustomType을 찾기
            Optional<CustomType> matchingType = findCustomTypeByPlaceholder(dataId);
            log.info("matchingType: {}", matchingType);

            matchingType.ifPresent(resultSet::add);
        }

        log.info("생성된 set: {}", resultSet);
        return resultSet;
    }

    private Optional<CustomType> findCustomTypeByPlaceholder(String placeholder) {
        for (CustomType customType : CustomType.values()) {
            if (customType.getPlaceholder().equals(placeholder)) {
                return Optional.of(customType);
            }
        }
        return Optional.empty();
    }

    private void checkUpload(List<CompletableFuture<Void>> uploadFutures) {
        try {
            CompletableFuture.allOf(uploadFutures.toArray(new CompletableFuture[0])).join();
            log.info("[MailManageUseCaseImpl] | 메일 예약 및 DynamoDB 업로드 성공!");
        } catch (CompletionException ex) {
            log.error("[MailManageUseCaseImpl] | DynamoDB 업로드 중 예외 발생: {}", ex.getMessage());
            throw new DynamodbUploadException();
        }
    }
}
