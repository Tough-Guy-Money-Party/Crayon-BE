package com.yoyomo.domain.club.application.mapper;

import static com.yoyomo.domain.club.application.dto.request.ClubRequest.*;
import static com.yoyomo.domain.club.application.dto.response.ClubResponse.*;
import static com.yoyomo.domain.club.application.dto.response.ClubResponse.Participation;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.yoyomo.domain.club.domain.entity.Club;

@Mapper(componentModel = "spring",
	unmappedTargetPolicy = ReportingPolicy.IGNORE,
	nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ClubMapper {

	Club from(Save dto);

	Response toResponse(Club club);

	Participation toParticipation(Club club);
}
