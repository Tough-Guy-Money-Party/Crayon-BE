package com.yoyomo.domain.application.application.mapper;

import com.yoyomo.domain.application.application.dto.req.ApplicationRequest;
import com.yoyomo.domain.application.application.dto.res.ApplicationDetailsResponse;
import com.yoyomo.domain.application.application.dto.res.ApplicationManageResponse;
import com.yoyomo.domain.application.application.dto.res.ApplicationResponse;
import com.yoyomo.domain.application.application.dto.res.MyApplicationsResponse;
import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.recruitment.domain.entity.Recruitment;
import com.yoyomo.domain.user.domain.entity.Applicant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ApplicationMapper {

    @Mapping(target = "id", ignore = true)
    Application from(Applicant applicant, Recruitment recruitment, ApplicationRequest request);

    ApplicationDetailsResponse mapToApplicationDetails(Application application);

//    @Mapping(target = "calendar", source = "application.recruitment.calendar")
    @Mapping(target = "id", source = "application.id")
    MyApplicationsResponse mapToMyApplications(Application application, Club club);

    ApplicationResponse mapToApplicationResponse(Application application);

    ApplicationManageResponse mapToApplicationManage(Application application);
}
