package com.yoyomo.domain.recruitment.application.mapper;

import com.yoyomo.domain.application.application.dto.response.ApplicationResponseDTO;
import com.yoyomo.domain.recruitment.application.dto.request.ProcessRequestDTO.Save;
import com.yoyomo.domain.recruitment.domain.entity.Process;
import com.yoyomo.domain.recruitment.domain.entity.Recruitment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

import static com.yoyomo.domain.recruitment.application.dto.request.ProcessRequestDTO.Update;
import static com.yoyomo.domain.recruitment.application.dto.response.ProcessResponseDTO.Response;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProcessMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "title", source = "dto.title")
    @Mapping(target = "startAt", source = "dto.period.evaluation.time.startAt")
    @Mapping(target = "endAt", source = "dto.period.evaluation.time.endAt")
    @Mapping(target = "announceStartAt", source = "dto.period.announcement.time.startAt")
    @Mapping(target = "announceEndAt", source = "dto.period.announcement.time.endAt")
    Process from(Save dto, Recruitment recruitment);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "title", source = "dto.title")
    @Mapping(target = "startAt", source = "dto.period.evaluation.time.startAt")
    @Mapping(target = "endAt", source = "dto.period.evaluation.time.endAt")
    @Mapping(target = "announceStartAt", source = "dto.period.announcement.time.startAt")
    @Mapping(target = "announceEndAt", source = "dto.period.announcement.time.endAt")
    Process from(Update dto, Recruitment recruitment);

    @Mapping(target = "applications", source = "applications")
    @Mapping(target = "applicantCount", expression = "java( applications.size() )")
    Response toResponse(Process process, List<ApplicationResponseDTO.Detail> applications);
}
