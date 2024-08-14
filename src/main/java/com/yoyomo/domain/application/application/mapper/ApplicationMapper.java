package com.yoyomo.domain.application.application.mapper;

import com.yoyomo.domain.application.application.dto.req.ApplicationRequest;
import com.yoyomo.domain.application.application.dto.res.*;
import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.application.exception.ApplicationNotFoundException;
import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.interview.application.dto.InterviewResponse;
import com.yoyomo.domain.recruitment.domain.entity.Process;
import com.yoyomo.domain.recruitment.domain.entity.ProcessType;
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
    @Mapping(target = "assessmentStatus" , expression = "java(AssessmentStatus.PENDING)")
    Application from(Applicant applicant, String recruitmentId, ApplicationRequest request);

    ApplicationDetailsResponse mapToApplicationDetails(Application application);

    @Mapping(target = "id", source = "application.id")
    MyApplicationsResponse mapToMyApplications(Application application, Club club);

    @Mapping(target = "id", source = "application.id")
    @Mapping(target = "currentStageTitle", expression = "java( findStageTitle(application, recruitment) )")
    ApplicationResponse mapToApplicationResponse(Application application, Recruitment recruitment);

    @Mapping(target = "interview", expression = "java( getInterviewResponseDto(application) )")
    @Mapping(target = "isBefore", expression = "java( isBefore(recruitment, application) )")
    ApplicationManageResponse mapToApplicationManage(Application application, Recruitment recruitment);

    default Boolean isBefore(Recruitment recruitment, Application application) {
        List<ProcessType> types = recruitment.getProcesses().stream()
                .map(Process::getType)
                .toList();

        if(!types.contains(ProcessType.INTERVIEW))
            return false;

        return types.indexOf(ProcessType.INTERVIEW) + 1 > application.getCurrentStage();
    }

    @Named("findStageTitle")
    default String findStageTitle(Application application, Recruitment recruitment) {
        return recruitment.getProcesses().stream()
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
}
