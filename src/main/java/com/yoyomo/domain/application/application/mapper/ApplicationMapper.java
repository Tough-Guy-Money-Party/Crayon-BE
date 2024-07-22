package com.yoyomo.domain.application.application.mapper;

import com.yoyomo.domain.application.application.dto.req.ApplicationRequest;
import com.yoyomo.domain.application.application.dto.res.ApplicationDetailsResponse;
import com.yoyomo.domain.application.application.dto.res.ApplicationManageResponse;
import com.yoyomo.domain.application.application.dto.res.ApplicationResponse;
import com.yoyomo.domain.application.application.dto.res.MyApplicationsResponse;
import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.application.exception.ApplicationNotFoundException;
import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.interview.application.dto.InterviewResponse;
import com.yoyomo.domain.item.application.dto.res.ItemResponse;
import com.yoyomo.domain.recruitment.domain.entity.Process;
import com.yoyomo.domain.recruitment.domain.entity.Recruitment;
import com.yoyomo.domain.user.domain.entity.Applicant;
import org.mapstruct.*;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ApplicationMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "assessments", expression = "java( new java.util.ArrayList<>() )")
    @Mapping(target = "createdAt", expression = "java( java.time.LocalDateTime.now() )")
    @Mapping(target = "assessmentStatus" , expression = "java(AssessmentStatus.BEFORE)")
    Application from(Applicant applicant, Recruitment recruitment, ApplicationRequest request);

    ApplicationDetailsResponse mapToApplicationDetails(Application application);

    //    @Mapping(target = "calendar", source = "application.recruitment.calendar")
    @Mapping(target = "id", source = "application.id")
    MyApplicationsResponse mapToMyApplications(Application application, Club club);

    @Mapping(target = "currentStageTitle", source = "application", qualifiedByName = "getStageTitle")
    ApplicationResponse mapToApplicationResponse(Application application);

    @Mappings({
            @Mapping(target = "interview", source = "application", qualifiedByName = "getInterviewResponseDto"),
            @Mapping(target = "items", source = "application", qualifiedByName = "getItemResponses")
    })
    ApplicationManageResponse mapToApplicationManage(Application application);

    @Named("getStageTitle")
    default String getStageTitle(Application application) {
        return application.getRecruitment().getProcesses().stream()
                .filter(process -> process.getStage() == application.getCurrentStage())
                .findAny()
                .map(Process::getTitle)
                .orElseThrow(ApplicationNotFoundException::new);
    }

    @Named("getInterviewResponseDto")
    default InterviewResponse getInterviewResponseDto(Application application) {
        return Optional.ofNullable(application.getInterview())
                .map(interview -> new InterviewResponse(interview.getPlace(), interview.getDate()))
                .orElse(null);  // 인터뷰 절차가 아니라면 인터뷰 데이터는 비워둬야 하므로
    }

    @Named("getItemResponses")
    default List<ItemResponse> getItemResponses(Application application) {
        return application.getRecruitment().getForm().getItems().stream()
                .map(ItemResponse::new)
                .toList();
    }
}
