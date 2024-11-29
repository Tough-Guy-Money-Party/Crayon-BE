package com.yoyomo.domain.recruitment.application.mapper;

import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.form.application.dto.response.FormResponseDTO;
import com.yoyomo.domain.recruitment.application.dto.response.ProcessResponseDTO;
import com.yoyomo.domain.recruitment.domain.entity.Recruitment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.time.LocalDateTime;
import java.util.List;

import static com.yoyomo.domain.recruitment.application.dto.request.RecruitmentRequestDTO.Save;
import static com.yoyomo.domain.recruitment.application.dto.response.RecruitmentResponseDTO.DetailResponse;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface RecruitmentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "club", source = "club")
    @Mapping(target = "startAt", expression = "java( getStartAt(dto) )")
    @Mapping(target = "endAt", expression = "java( getEndAt(dto) )")
    Recruitment from(Save dto, Club club);

    @Mapping(target = "processes", source = "processes")
    @Mapping(target = "title", source = "recruitment.title")
    @Mapping(target = "clubName", expression = "java(recruitment.getClub().getName() )")
    @Mapping(target = "processCount", expression = "java( processes.size() )")
    DetailResponse toDetailResponse(Recruitment recruitment, List<ProcessResponseDTO.Response> processes, FormResponseDTO.info form);


    default LocalDateTime getStartAt(Save dto) {
        return dto.processes().get(0).period().evaluation().time().startAt();
    }

    default LocalDateTime getEndAt(Save dto) {
        return dto.processes().get(dto.processes().size() - 1).period().announcement().time().endAt();
    }
}
