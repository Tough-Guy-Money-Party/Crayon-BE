package com.yoyomo.domain.application.application.mapper;

import com.yoyomo.domain.application.application.dto.request.ApplicationRequestDTO.Save;
import com.yoyomo.domain.application.application.dto.response.ResultResponseDTO.Result;
import com.yoyomo.domain.application.domain.entity.Answer;
import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.application.domain.entity.enums.Status;
import com.yoyomo.domain.recruitment.domain.entity.Process;
import com.yoyomo.domain.recruitment.domain.entity.Recruitment;
import com.yoyomo.domain.user.domain.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import static com.yoyomo.domain.application.application.dto.response.ApplicationResponseDTO.*;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ApplicationMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", expression = "java( getUser(dto) )")
    Application from(Save dto, Process process);

    // 수정: Application 도메인 생성 시 개발
    @Mapping(target = "id", source = "application.id")
    Detail toDetail(Application application, Answer answer);

    @Mapping(target = "result", expression = "java( getResult(application) )")
    Result toResult(Application application);

    default Status getResult(Application application) {
        Recruitment recruitment = application.getProcess().getRecruitment();

        if(recruitment.getProcesses().size() - 1 == application.getProcess().getStage())
            return application.getStatus();
        return Status.PENDING;
    }

    @Mapping(target = "club", source = "application.process.recruitment.club")
    @Mapping(target = "processes", source = "application.process.recruitment.processes")
    Response toResponses(Application application);

    default User getUser(Save dto) {
        return new User(dto.name(), dto.email(), dto.tel());
    }

    @Mapping(target = "id", source = "application.id")
    MyResponse toMyResponse(Application application, Answer answer);
}
