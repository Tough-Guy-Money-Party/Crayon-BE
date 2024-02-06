package com.yoyomo.domain.recruitment.application.mapper;

import com.yoyomo.domain.form.domain.entity.Form;
import com.yoyomo.domain.recruitment.application.dto.req.RecruitmentRequest;
import com.yoyomo.domain.recruitment.application.dto.res.RecruitmentDetailsResponse;
import com.yoyomo.domain.recruitment.application.dto.res.RecruitmentResponse;
import com.yoyomo.domain.recruitment.domain.entity.Recruitment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface RecruitmentMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "clubId", source = "request.clubId")
    @Mapping(target = "form", source = "form")
    Recruitment from(RecruitmentRequest request, Form form);

    RecruitmentDetailsResponse mapToRecruitmentDetails(Recruitment recruitment);

    RecruitmentResponse mapToRecruitmentResponse(Recruitment recruitment);
}
