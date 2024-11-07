package com.yoyomo.domain.mail.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface MailMapper {

//    @Mapping(target = "templateId", source = "dto.templateId")
//    @Mapping(target = "id", expression = "java(java.util.UUID.randomUUID().toString())")
//    @Mapping(target = "customData", source = "customData")
//    @Mapping(target = "from", source = "dto.from")
//    @Mapping(target = "scheduledTime", source = "dto.scheduledTime")
//    @Mapping(target = "status", expression = "java(\"scheduled\")")
//    Mail toMail(MailRequest.Reserve dto, String to, Map<String, String> customData);
}
