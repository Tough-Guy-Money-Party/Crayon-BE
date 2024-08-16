package com.yoyomo.domain.user.application.mapper;

import com.yoyomo.domain.user.application.dto.response.ManagerResponseDTO;
import com.yoyomo.domain.user.application.dto.response.ManagerResponseDTO.ManagerInfo;
import com.yoyomo.domain.user.domain.entity.Manager;
import com.yoyomo.global.config.jwt.presentation.JwtResponse;
import com.yoyomo.global.config.kakao.dto.KakaoAccount;
import org.mapstruct.*;

import static com.yoyomo.domain.user.application.dto.response.ManagerResponseDTO.*;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ManagerMapper {

    Response toResponseDTO(Manager manager, JwtResponse token);

    ManagerInfo toManagerInfoDTO(Manager manager);

    @Mapping(target = "name", source = "dto.profile.nickname")
    Manager from(KakaoAccount dto);
}
