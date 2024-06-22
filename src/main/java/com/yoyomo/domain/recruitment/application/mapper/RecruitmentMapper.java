package com.yoyomo.domain.recruitment.application.mapper;

import com.yoyomo.domain.application.domain.service.ApplicationGetService;
import com.yoyomo.domain.form.domain.entity.Form;
import com.yoyomo.domain.recruitment.application.dto.req.RecruitmentRequest;
import com.yoyomo.domain.recruitment.application.dto.res.RecruitmentDetailsResponse;
import com.yoyomo.domain.recruitment.application.dto.res.RecruitmentResponse;
import com.yoyomo.domain.recruitment.domain.entity.Recruitment;
import com.yoyomo.domain.recruitment.domain.service.RecruitmentGetService;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface RecruitmentMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "clubId", source = "request.clubId")
    @Mapping(target = "form", source = "form")
    @Mapping(target = "processStage", expression = "java(0)")
    @Mapping(source = "request.title", target = "title")
    Recruitment from(RecruitmentRequest request, Form form);

    RecruitmentDetailsResponse mapToRecruitmentDetails(Recruitment recruitment);

    @Mapping(target = "recruitmentEndDate", expression = "java( recruitmentGetService.getRecruitmentEndDate(recruitment.getId()))")
    @Mapping(target = "totalApplicantsCount", expression = "java( applicationGetService.getTotalApplicantsCount(recruitment.getId()))")
    @Mapping(target = "acceptedApplicantsCount", expression = "java( applicationGetService.getAcceptedApplicantsCount(recruitment.getId()))")
    @Mapping(target = "rejectedApplicantsCount", expression = "java( applicationGetService.getRejectedApplicantsCount(recruitment.getId()))")
    RecruitmentResponse mapToRecruitmentResponse(Recruitment recruitment, @Context ApplicationGetService applicationGetService, @Context RecruitmentGetService recruitmentGetService);
}
