package com.yoyomo.domain.mail.application.dto.request;

import com.yoyomo.domain.mail.domain.entity.CommonData;
import com.yoyomo.domain.mail.domain.entity.CustomData;
import com.yoyomo.domain.mail.domain.entity.Mail;
import com.yoyomo.domain.mail.domain.entity.enums.CustomType;
import com.yoyomo.domain.mail.domain.entity.enums.Status;
import com.yoyomo.domain.template.application.dto.request.MailTemplateSaveRequest;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
public record MailRequest(
        @NotNull Long processId,
        @NotNull LocalDateTime scheduledTime,
        @NotNull MailTemplateSaveRequest passTemplate,
        @NotNull MailTemplateSaveRequest failTemplate
) {

    public Mail toMail(String source, CommonData commonData, CustomData customData, UUID templateId) {
        // UTC로 변환 후 1시간 추가
        long ttl = scheduledTime.minusHours(9).plusDays(1)
                .toInstant(ZoneOffset.UTC)
                .getEpochSecond();

        Map<CustomType, String> data = new HashMap<>();
        data.putAll(commonData.getData());
        data.putAll(customData.getData());

        return Mail.builder()
                .id(UUID.randomUUID().toString())
                .templateId(templateId.toString())
                .customData(data)
                .source(source)
                .destination(customData.getEmail())
                .scheduledTime(scheduledTime)
                .status(Status.SCHEDULED)
                .ttl(ttl)
                .processId(processId)
                .build();
    }
}
