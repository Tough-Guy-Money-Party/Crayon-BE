package com.yoyomo.domain.recruitment.application.mapper;

import com.yoyomo.domain.application.domain.service.ApplicationGetService;
import com.yoyomo.domain.interview.domain.entity.Interview;
import com.yoyomo.domain.recruitment.application.dto.req.RecruitmentRequest;
import com.yoyomo.domain.recruitment.application.dto.res.ProcessResultResponse;
import com.yoyomo.domain.recruitment.application.dto.res.ProcessResultsResponse;
import com.yoyomo.domain.recruitment.application.dto.res.RecruitmentDetailsResponse;
import com.yoyomo.domain.recruitment.application.dto.res.RecruitmentResponse;
import com.yoyomo.domain.recruitment.domain.entity.Process;
import com.yoyomo.domain.recruitment.domain.entity.Recruitment;
import com.yoyomo.domain.recruitment.domain.service.RecruitmentGetService;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface RecruitmentMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "processCount", expression = "java( request.getProcessCount() )")
    Recruitment from(RecruitmentRequest request, String clubId);

    RecruitmentDetailsResponse mapToRecruitmentDetails(Recruitment recruitment);

    @Mapping(target = "recruitmentEndDate", expression = "java( recruitmentGetService.getRecruitmentEndDate(recruitment.getId()))")
    @Mapping(target = "totalApplicantsCount", expression = "java( applicationGetService.getTotalApplicantsCount(recruitment.getId()))")
    @Mapping(target = "acceptedApplicantsCount", expression = "java( applicationGetService.getAcceptedApplicantsCount(recruitment.getId()))")
    @Mapping(target = "rejectedApplicantsCount", expression = "java( applicationGetService.getRejectedApplicantsCount(recruitment.getId()))")
    RecruitmentResponse mapToRecruitmentResponse(Recruitment recruitment, @Context ApplicationGetService applicationGetService, @Context RecruitmentGetService recruitmentGetService);

    @Mapping(target = "processTitle", source = "process.title")
    ProcessResultsResponse mapToProcessResultsResponse(String recruitmentTitle, Process process);

    ProcessResultResponse mapToProcessResultResponse(String clubName, String name, String recruitmentTitle, Interview interview);

    ProcessResultResponse mapToProcessResultResponse(String clubName, String name, String recruitmentTitle);
}
