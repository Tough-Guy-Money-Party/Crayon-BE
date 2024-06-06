package com.yoyomo.domain.application.application.mapper;

import com.yoyomo.domain.application.application.dto.req.ApplicationRequest;
import com.yoyomo.domain.application.application.dto.res.ApplicationDetailsResponse;
import com.yoyomo.domain.application.application.dto.res.ApplicationManageResponse;
import com.yoyomo.domain.application.application.dto.res.ApplicationResponse;
import com.yoyomo.domain.application.application.dto.res.MyApplicationsResponse;
import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.application.domain.service.ApplicationGetService;
import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.recruitment.domain.entity.Recruitment;
import com.yoyomo.domain.user.domain.entity.Applicant;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ApplicationMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "assessments", expression = "java( new java.util.ArrayList<>() )")
    @Mapping(target = "createdAt", expression = "java( java.time.LocalDateTime.now() )")
    Application from(Applicant applicant, Recruitment recruitment, ApplicationRequest request);

    ApplicationDetailsResponse mapToApplicationDetails(Application application);

    //    @Mapping(target = "calendar", source = "application.recruitment.calendar")
    @Mapping(target = "id", source = "application.id")
    MyApplicationsResponse mapToMyApplications(Application application, Club club);

    ApplicationResponse mapToApplicationResponse(Application application);

    @Mapping(target = "currentStageApplicants", expression = "java( applicationGetService.findApplicantsByStage(application.getRecruitment().getId(), application.getApplicationStage()))")
    ApplicationManageResponse mapToApplicationManage(Application application, @Context ApplicationGetService applicationGetService);
}
