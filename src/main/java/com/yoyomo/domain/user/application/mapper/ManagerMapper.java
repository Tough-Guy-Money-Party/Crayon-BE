package com.yoyomo.domain.user.application.mapper;

import static com.yoyomo.domain.user.application.dto.response.ManagerResponseDTO.Response;

import com.yoyomo.domain.user.application.dto.response.ManagerResponseDTO.ManagerInfo;
import com.yoyomo.domain.user.domain.entity.User;
import com.yoyomo.global.config.jwt.presentation.JwtResponse;
import com.yoyomo.global.config.kakao.dto.KakaoAccount;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ManagerMapper {

    Response toResponseDTO(User user, JwtResponse token);

    ManagerInfo toManagerInfoDTO(User user);

    @Mapping(target = "name", source = "dto.profile.nickname")
    User from(KakaoAccount dto);
}
