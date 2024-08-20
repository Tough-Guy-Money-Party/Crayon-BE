package com.yoyomo.domain.process.application.mapper;

import com.yoyomo.domain.application.application.dto.response.ApplicationResponseDTO;
import com.yoyomo.domain.process.application.dto.request.ProcessRequestDTO.Save;
import com.yoyomo.domain.process.domain.entity.Process;
import com.yoyomo.domain.recruitment.domain.entity.Recruitment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

import static com.yoyomo.domain.process.application.dto.request.ProcessRequestDTO.Update;
import static com.yoyomo.domain.process.application.dto.response.ProcessResponseDTO.Response;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProcessMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "title", source = "dto.title")
    Process from(Save dto, Recruitment recruitment);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "title", source = "dto.title")
    Process from(Update dto, Recruitment recruitment);

    @Mapping(target = "applications", source = "applications")
    Response toResponse(Process process, List<ApplicationResponseDTO.Response> applications);
}
