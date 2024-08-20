package com.yoyomo.domain.recruitment.application.mapper;

import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.process.application.dto.response.ProcessResponseDTO;
import com.yoyomo.domain.process.domain.entity.Process;
import com.yoyomo.domain.recruitment.application.dto.request.RecruitmentRequestDTO;
import com.yoyomo.domain.recruitment.application.dto.response.RecruitmentResponseDTO;
import com.yoyomo.domain.recruitment.application.dto.response.RecruitmentResponseDTO.DetailResponse;
import com.yoyomo.domain.recruitment.domain.entity.Recruitment;
import com.yoyomo.domain.recruitment.exception.ProcessEmptyException;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import static com.yoyomo.domain.recruitment.application.dto.response.RecruitmentResponseDTO.*;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface RecruitmentMapper {

    @Mapping(target = "id", ignore = true)
    Recruitment from(RecruitmentRequestDTO.Save dto, Club club);

    @Mapping(target = "processes", source = "processes")
    @Mapping(target = "processCount", expression = "java( processes.size() )")
    DetailResponse toDetailResponse(Recruitment recruitment, List<ProcessResponseDTO.Response> processes);

    @Mapping(target = "recruitmentEndDate", expression = "java( getEndDate(recruitment) )")
    Response toResponse(Recruitment recruitment);

    default LocalDate getEndDate(Recruitment recruitment) {
        return recruitment.getProcesses().stream()
                .max(Comparator.comparing(Process::getEndAt))
                .map(process -> process.getEndAt().toLocalDate())
                .orElseThrow(ProcessEmptyException::new);
    }
}
