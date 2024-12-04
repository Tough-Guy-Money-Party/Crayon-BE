package com.yoyomo.domain.application.application.mapper;

import static com.yoyomo.domain.application.application.dto.response.ApplicationResponseDTO.MyResponse;
import static com.yoyomo.domain.application.application.dto.response.ApplicationResponseDTO.Response;

import com.yoyomo.domain.application.application.dto.request.ApplicationRequestDTO.Save;
import com.yoyomo.domain.application.domain.entity.Answer;
import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.recruitment.domain.entity.Process;
import com.yoyomo.domain.recruitment.domain.entity.enums.Type;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ApplicationMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    Application from(Save dto, Process process);

    @Mapping(target = "club", source = "application.process.recruitment.club")
    @Mapping(target = "processes", source = "application.process.recruitment.processes")
    @Mapping(target = "currentStage", source = "application.process.stage")
    Response toResponse(Application application);

    // 수정: Evaluation 도메인 생성 시 개발
    @Mapping(target = "id", source = "application.id")
    MyResponse toMyResponse(Application application, Answer answer);

    default Boolean isBefore(Application application) {
        List<Type> types = application.getProcess().getRecruitment().getProcesses().stream()
                .map(Process::getType)
                .toList();

        if (!types.contains(Type.INTERVIEW)) {
            return false;
        }

        return types.indexOf(Type.INTERVIEW) > application.getProcess().getStage();
    }

}
