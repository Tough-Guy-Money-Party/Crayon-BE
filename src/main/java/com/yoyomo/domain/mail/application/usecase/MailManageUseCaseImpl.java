package com.yoyomo.domain.mail.application.usecase;

import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.application.domain.entity.enums.Status;
import com.yoyomo.domain.application.domain.service.ApplicationGetService;
import com.yoyomo.domain.application.domain.service.ProcessResultGetService;
import com.yoyomo.domain.club.domain.service.ClubManagerAuthService;
import com.yoyomo.domain.mail.application.dto.request.MailRequest;
import com.yoyomo.domain.mail.application.dto.request.MailUpdateRequest;
import com.yoyomo.domain.mail.domain.entity.CommonData;
import com.yoyomo.domain.mail.domain.entity.CustomData;
import com.yoyomo.domain.mail.domain.entity.Mail;
import com.yoyomo.domain.mail.domain.service.MailSaveService;
import com.yoyomo.domain.mail.domain.service.MailUpdateService;
import com.yoyomo.domain.mail.exception.DynamodbUploadException;
import com.yoyomo.domain.mail.exception.LambdaInvokeException;
import com.yoyomo.domain.mail.exception.MailCancelException;
import com.yoyomo.domain.mail.exception.MailUpdateException;
import com.yoyomo.domain.recruitment.domain.entity.Process;
import com.yoyomo.domain.recruitment.domain.entity.Recruitment;
import com.yoyomo.domain.recruitment.domain.service.ProcessGetService;
import com.yoyomo.domain.template.domain.service.MailTemplateSaveService;
import com.yoyomo.domain.user.domain.entity.User;
import com.yoyomo.global.common.util.BatchDivider;
import com.yoyomo.infra.aws.lambda.service.LambdaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

import static com.yoyomo.domain.mail.presentation.constant.ResponseMessage.MAIL_UPDATE_FAIL;


@Slf4j
@Service
@RequiredArgsConstructor
public class MailManageUseCaseImpl {

    private static final int PAGE_SIZE = 100;

    private final MailSaveService mailSaveService;
    private final MailUpdateService mailUpdateService;
    private final ApplicationGetService applicationGetService;
    private final ProcessGetService processGetService;
    private final MailTemplateSaveService mailTemplateSaveService;
    private final LambdaService lambdaService;
    private final ClubManagerAuthService clubManagerAuthService;
    private final BatchDivider batchDivider;
    private final ProcessResultGetService processResultGetService;

    @Value("${mail.lambda.arn}")
    private String mailLambdaArn;

    @Value("${mail.sourceAddress}")
    private String mailSourceAddress;

    @Transactional
    public void reserve(MailRequest dto, User user) {
        create(dto, user);
    }

    @Transactional
    public void direct(MailRequest dto, User user) {
        create(dto, user);
        CompletableFuture<Void> lambdaInvocation = lambdaService.invokeLambdaAsync(mailLambdaArn);

        lambdaInvocation.thenRun(() ->
                log.info("[MailManageUseCaseImpl] Lambda 호출 성공")
        ).exceptionally(e -> {
            throw new LambdaInvokeException(e.getMessage());
        });
    }

    @Transactional
    public void cancel(long processId, User user) {
        Process process = checkAuthorityByProcessId(processId, user);

        process.checkMailScheduled();

        try {
            mailUpdateService.cancelMail(processId).join();
            process.cancelMail();
        } catch (CompletionException e) {
            throw new MailCancelException(e.getMessage());
        }
    }

    @Transactional
    public void update(long processId, MailUpdateRequest dto, User user) {
        Process process = checkAuthorityByProcessId(processId, user);

        process.checkMailScheduled();

        if (dto.scheduledTime().isBefore(LocalDateTime.now())) {
            throw new MailUpdateException(MAIL_UPDATE_FAIL.getMessage());
        }

        try {
            mailUpdateService.updateScheduledTime(processId, dto).join();
            process.updateSchedule(dto.scheduledTime());
        } catch (CompletionException e) {
            throw new MailUpdateException(e.getMessage());
        }
    }

    private void create(MailRequest dto, User user) {
        long processId = dto.processId();

        Process process = checkAuthorityByProcessId(processId, user);

        process.reserve(dto.scheduledTime());

        Recruitment recruitment = process.getRecruitment();

        List<Application> applications = applicationGetService.findAll(process);
        List<Mail> mails = createMail(applications, process, dto, recruitment);
        List<CompletableFuture<Void>> uploadFutures = batchDivider.divide(mails, PAGE_SIZE)
                .stream()
                .map(mailSaveService::upload)
                .toList();

        checkUpload(uploadFutures);
    }

    private List<Mail> createMail(List<Application> applications, Process process, MailRequest dto, Recruitment recruitment) {
        UUID passTemplateId = mailTemplateSaveService.uploadTemplate(dto.passTemplate());
        UUID failTemplateId = mailTemplateSaveService.uploadTemplate(dto.failTemplate());

        Map<UUID, Status> processResults = processResultGetService.findAll(process, applications);
        CommonData commonData = CommonData.of(process, recruitment);
        List<CustomData> customData = applications.stream()
                .map(application -> CustomData.of(application, processResults.getOrDefault(application.getId(), Status.BEFORE_EVALUATION)))
                .toList();

        return customData.stream()
                .map(data -> {
                    UUID templateId = data.isPass() ? passTemplateId : failTemplateId;
                    return dto.toMail(mailSourceAddress, commonData, data, templateId);
                })
                .toList();
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

    private Process checkAuthorityByProcessId(Long processId, User user) {
        Process process = processGetService.find(processId);
        clubManagerAuthService.checkAuthorization(process, user);

        return process;
    }
}
