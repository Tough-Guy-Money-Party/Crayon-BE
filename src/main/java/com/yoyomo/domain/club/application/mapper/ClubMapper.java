package com.yoyomo.domain.club.application.mapper;

import com.yoyomo.domain.club.application.dto.req.ClubRequest;
import com.yoyomo.domain.club.application.dto.res.ClubManagerResponse;
import com.yoyomo.domain.club.application.dto.res.ClubResponse;
import com.yoyomo.domain.club.domain.entity.Club;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ClubMapper {
    @Mapping(target = "managers", expression = "java( new java.util.ArrayList<>() )")
    Club from(ClubRequest request);


    ClubResponse clubToClubResponse(Club club);
}
