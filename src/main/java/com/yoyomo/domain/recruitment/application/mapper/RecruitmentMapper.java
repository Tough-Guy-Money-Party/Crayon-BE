package com.yoyomo.domain.recruitment.application.mapper;

import com.yoyomo.domain.recruitment.application.dto.req.RecruitmentRequest;
import com.yoyomo.domain.recruitment.domain.entity.Recruitment;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface RecruitmentMapper {
    Recruitment from(String formId, RecruitmentRequest request);
}
