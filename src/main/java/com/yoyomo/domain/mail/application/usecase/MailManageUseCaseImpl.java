package com.yoyomo.domain.mail.application.usecase;

import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.application.domain.service.ApplicationGetService;
import com.yoyomo.domain.mail.domain.entity.Mail;
import com.yoyomo.domain.mail.domain.entity.enums.CustomType;
import com.yoyomo.domain.mail.domain.service.MailReserveService;
import com.yoyomo.domain.mail.domain.service.MailSaveService;
import com.yoyomo.domain.mail.exception.DynamodbUploadException;
import com.yoyomo.domain.recruitment.domain.entity.Process;
import com.yoyomo.domain.recruitment.domain.entity.Recruitment;
import com.yoyomo.domain.recruitment.domain.service.ProcessGetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.yoyomo.domain.mail.application.dto.MailRequest.Reserve;
import static com.yoyomo.domain.mail.application.dto.MailRequest.toMail;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailManageUseCaseImpl implements MailManageUseCase {
    private static final int PAGE_SIZE = 100;

    private final MailSaveService mailSaveService;
    private final MailReserveService mailReservationService;
    private final ApplicationGetService applicationGetService;
    private final ProcessGetService processGetService;

    @Override// 예외처리(존재하는 템플릿과 맞는지,
    public void reserve(Reserve dto) {
//        mailReservationService.create(dto);
        create(dto);
    }

    private void create(Reserve dto) {
        long processId = dto.processId();

        Process process = processGetService.find(processId);
        Recruitment recruitment = process.getRecruitment();

        List<CompletableFuture<Void>> uploadFutures = new ArrayList<>();

        Stream.iterate(0, pageNumber -> pageNumber + 1)
                .map(pageNumber -> applicationGetService.findAll(processId, pageNumber, PAGE_SIZE))
                .takeWhile(applications -> !applications.isEmpty())
                .forEach(applications -> uploadApplications(applications, uploadFutures, dto, recruitment));

        checkUpload(uploadFutures);
    }

    private void uploadApplications(List<Application> applications, List<CompletableFuture<Void>> uploadFutures, Reserve dto, Recruitment recruitment) {
        List<Mail> mails = convertToMails(applications, dto, recruitment);
        CompletableFuture<Void> uploadFuture = mailSaveService.upload(mails);
        uploadFutures.add(uploadFuture);
    }

    private List<Mail> convertToMails(List<Application> applications, Reserve dto, Recruitment recruitment) {
        return applications.stream()
                .map(application -> convert(dto, application, recruitment))
                .collect(Collectors.toList());
    }

    private Mail convert(Reserve dto, Application application, Recruitment recruitment) {
        Map<String, String> customData = createCustomData(application, recruitment, dto.customType());
        String destination = application.getUser().getEmail();

        return toMail(dto, destination , customData);
    }

    private Map<String, String> createCustomData(Application application, Recruitment recruitment, Set<CustomType> customTypes) {
        Map<String, String> dataMap = new HashMap<>();

        for (CustomType customType : customTypes) {
            String value = customType.extractValue(application, recruitment);
            dataMap.put(customType.getPlaceholder(), value);
        }
        return dataMap;
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
