package com.yoyomo.domain.landing.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.landing.application.dto.response.LandingResponse.General;
import com.yoyomo.domain.landing.application.dto.response.LandingResponse.Style;
import com.yoyomo.domain.landing.domain.entity.Landing;

@Mapper(componentModel = "spring",
	unmappedTargetPolicy = ReportingPolicy.IGNORE,
	nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface LandingMapper {
	General toGeneralResponse(Club club, Landing landing);

	Style toStyleResponse(Club club, Landing landing);
}
