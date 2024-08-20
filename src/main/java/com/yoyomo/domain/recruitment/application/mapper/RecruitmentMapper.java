package com.yoyomo.domain.recruitment.application.mapper;

import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.process.application.dto.response.ProcessResponseDTO;
import com.yoyomo.domain.recruitment.application.dto.request.RecruitmentRequestDTO;
import com.yoyomo.domain.recruitment.application.dto.response.RecruitmentResponseDTO;
import com.yoyomo.domain.recruitment.domain.entity.Recruitment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface RecruitmentMapper {

    @Mapping(target = "id", ignore = true)
    Recruitment from(RecruitmentRequestDTO.Save dto, Club club);

    @Mapping(target = "processes", source = "processes")
    @Mapping(target = "processCount", expression = "java( processes.size() )")
    RecruitmentResponseDTO.Response toResponse(Recruitment recruitment, List<ProcessResponseDTO.Response> processes);
}
