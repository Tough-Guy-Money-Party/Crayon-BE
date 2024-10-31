package com.yoyomo.domain.mail.application.dto;

import com.yoyomo.domain.mail.domain.entity.enums.CustomType;

import java.time.LocalDateTime;
import java.util.Set;

public class MailRequest {

    public record Reserve(
            Long processId,
            LocalDateTime scheduledTime,
            String templateId,
            Set<CustomType> customType,
            String from
    ) {
    }

}
