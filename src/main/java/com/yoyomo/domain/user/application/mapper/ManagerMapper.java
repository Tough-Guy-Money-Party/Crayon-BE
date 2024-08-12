package com.yoyomo.domain.user.application.mapper;

import com.yoyomo.domain.user.application.dto.ManagerDTO;
import com.yoyomo.domain.user.domain.entity.Manager;
import com.yoyomo.global.config.jwt.presentation.JwtResponse;
import com.yoyomo.global.config.kakao.dto.KakaoAccount;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ManagerMapper {

    ManagerDTO.Response to(Manager manager, JwtResponse token);

    @Mapping(target = "name", source = "dto.profile.nickname")
    Manager from(KakaoAccount dto);
}
