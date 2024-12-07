package com.yoyomo.domain.user.application.mapper;

import com.yoyomo.domain.user.application.dto.response.UserResponseDTO.Response;
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
public interface UserMapper {

    @Mapping(target = "name", source = "dto.profile.nickname")
    User from(KakaoAccount dto);

    Response toResponseDTO(User user, JwtResponse token);
}
