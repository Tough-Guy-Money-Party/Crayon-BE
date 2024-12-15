package com.yoyomo.domain.application.application.mapper;

import com.yoyomo.domain.application.domain.entity.Application;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import static com.yoyomo.domain.application.application.dto.response.ApplicationResponseDTO.Response;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ApplicationMapper {

    @Mapping(target = "club", source = "application.process.recruitment.club")
    @Mapping(target = "processes", source = "application.process.recruitment.processes")
    @Mapping(target = "currentStage", source = "application.process.stage")
    Response toResponse(Application application);
}
