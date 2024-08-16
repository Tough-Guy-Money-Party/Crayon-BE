package com.yoyomo.domain.club.application.mapper;

import com.yoyomo.domain.club.application.dto.request.ClubRequestDTO;
import com.yoyomo.domain.club.domain.entity.Club;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import static com.yoyomo.domain.club.application.dto.response.ClubResponseDTO.Participation;
import static com.yoyomo.domain.club.application.dto.response.ClubResponseDTO.Response;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ClubMapper {

    Club from(ClubRequestDTO.Save dto);

    Response toResponse(Club club);

    Participation toParticipation(Club club);
}
