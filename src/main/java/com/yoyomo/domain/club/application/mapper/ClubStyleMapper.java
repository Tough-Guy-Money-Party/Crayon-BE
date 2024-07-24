package com.yoyomo.domain.club.application.mapper;


import com.yoyomo.domain.club.application.dto.req.UpdateStyleSettingsRequest;
import com.yoyomo.domain.club.application.dto.res.ClubStyleSettingsResponse;
import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.club.domain.entity.ClubLandingStyle;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ClubStyleMapper {
    ClubLandingStyle from(UpdateStyleSettingsRequest request);

    ClubStyleSettingsResponse ClubLandingStyleToClubStyleSettingsResponse(ClubLandingStyle clubLandingStyle);
}
