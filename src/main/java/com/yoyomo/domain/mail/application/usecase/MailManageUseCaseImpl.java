package com.yoyomo.domain.mail.application.usecase;

import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.application.domain.entity.enums.Status;
import com.yoyomo.domain.application.domain.service.ApplicationGetService;
import com.yoyomo.domain.mail.application.dto.request.MailRequest;
import com.yoyomo.domain.mail.domain.entity.Mail;
import com.yoyomo.domain.mail.domain.entity.enums.CustomType;
import com.yoyomo.domain.mail.domain.service.MailSaveService;
import com.yoyomo.domain.mail.domain.service.MailUtilService;
import com.yoyomo.domain.mail.exception.DynamodbUploadException;
import com.yoyomo.domain.mail.exception.LambdaInvokeException;
import com.yoyomo.domain.recruitment.domain.entity.Process;
import com.yoyomo.domain.recruitment.domain.entity.Recruitment;
import com.yoyomo.domain.recruitment.domain.service.ProcessGetService;
import com.yoyomo.domain.template.domain.service.MailTemplateSaveService;
import com.yoyomo.infra.aws.lambda.service.LambdaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Slf4j
@Service
@RequiredArgsConstructor
public class MailManageUseCaseImpl {
    private static final int PAGE_SIZE = 100;

    private final MailSaveService mailSaveService;
    private final MailUtilService mailUtilService;
    private final ApplicationGetService applicationGetService;
    private final ProcessGetService processGetService;
    private final MailTemplateSaveService mailTemplateSaveService;

    private final LambdaService lambdaService;

    @Value("${mail.lambda.arn}")
    private String mailLambdaArn;

    @Value("${mail.sourceAddress}")
    private String mailSourceAddress;

    public void reserve(MailRequest dto) {
        create(dto);
    }

    public void direct(MailRequest dto) {
        create(dto);
        CompletableFuture<Void> lambdaInvocation = lambdaService.invokeLambdaAsync(mailLambdaArn);

        lambdaInvocation.thenRun(() ->
                log.info("[MailManageUseCaseImpl] Lambda 호출 성공: {}", mailLambdaArn)
        ).exceptionally(e -> {
            log.error("[MailManageUseCaseImpl] Lambda 호출 실패: {}", e.getMessage());
            throw new LambdaInvokeException();
        });
    }

    private void create(MailRequest dto) {
        long processId = dto.processId();

        Process process = processGetService.find(processId);
        Recruitment recruitment = process.getRecruitment();

        List<CompletableFuture<Void>> uploadFutures = new ArrayList<>();

        process(processId, uploadFutures, dto, recruitment);
        checkUpload(uploadFutures);
    }

    private void process(long processId, List<CompletableFuture<Void>> uploadFutures, MailRequest dto, Recruitment recruitment) {
        Stream.iterate(0, pageNumber -> pageNumber + 1)
                .map(pageNumber -> applicationGetService.findAll(processId, pageNumber, PAGE_SIZE))
                .takeWhile(applications -> !applications.isEmpty())
                .forEach(applications -> uploadApplications(applications, uploadFutures, dto, recruitment));
    }

    private void uploadApplications(List<Application> applications, List<CompletableFuture<Void>> uploadFutures, MailRequest dto, Recruitment recruitment) {
        Set<CustomType> passCustomType = mailUtilService.extract(dto.passTemplate());
        Set<CustomType> failCustomType = mailUtilService.extract(dto.failTemplate());

        UUID passTemplateId = mailTemplateSaveService.uploadTemplate(dto.passTemplate());
        UUID failTemplateId = mailTemplateSaveService.uploadTemplate(dto.failTemplate());

        List<Mail> mailList = convertToMails(applications, dto, recruitment, passCustomType, passTemplateId, failCustomType, failTemplateId);

        CompletableFuture<Void> uploadFuture = mailSaveService.upload(mailList);
        uploadFutures.add(uploadFuture);
    }

    private List<Mail> convertToMails(List<Application> applications, MailRequest dto, Recruitment recruitment,
                                      Set<CustomType> passCustomTypes, UUID passTemplateId,
                                      Set<CustomType> failCustomTypes, UUID failTemplateId) {
        return applications.stream()
                .map(application -> {
                    boolean isPass = application.getStatus() == Status.PASS;
                    UUID templateId = isPass ? passTemplateId : failTemplateId;
                    Set<CustomType> customTypes = isPass ? passCustomTypes : failCustomTypes;
                    return convert(dto, application, recruitment, customTypes, templateId);
                })
                .collect(Collectors.toList());
    }

    private Mail convert(MailRequest dto, Application application, Recruitment recruitment,
                         Set<CustomType> customTypes, UUID templateId) {
        Map<String, String> customData = mailUtilService.createCustomData(application, recruitment, customTypes);
            String destination = application.getEmail();
        return dto.toMail(mailSourceAddress, destination, customData, templateId);
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
