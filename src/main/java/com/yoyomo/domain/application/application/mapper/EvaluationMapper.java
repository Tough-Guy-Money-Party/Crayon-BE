package com.yoyomo.domain.application.application.mapper;

import com.yoyomo.domain.application.application.dto.request.EvaluationRequestDTO.Save;
import com.yoyomo.domain.application.application.dto.response.EvaluationResponseDTO.Response;
import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.application.domain.entity.Evaluation;
import com.yoyomo.domain.user.domain.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface EvaluationMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "application", source = "application")
    Evaluation from(Save dto, User manager, Application application);

    @Mapping(target = "managerName", source = "evaluation.manager.name")
    Response toResponse(Evaluation evaluation);
}
