package com.yoyomo.domain.mail.application.mapper;

import com.yoyomo.domain.mail.domain.entity.Mail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.Map;

import static com.yoyomo.domain.mail.application.dto.MailRequest.Reserve;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface MailMapper {

    @Mapping(target = "templateId", source = "dto.templateId")
    @Mapping(target = "id", expression = "java(java.util.UUID.randomUUID().toString())")
    @Mapping(target = "customData", source = "customData")
    @Mapping(target = "from", source = "dto.from")
    @Mapping(target = "scheduledTime", source = "dto.scheduledTime")
    @Mapping(target = "status", expression = "java(\"scheduled\")")
    Mail toMail(Reserve dto, String to, Map<String, String> customData);
}
