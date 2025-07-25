package com.yoyomo.domain.user.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.yoyomo.domain.user.application.dto.response.UserResponse.Response;
import com.yoyomo.domain.user.domain.entity.User;
import com.yoyomo.global.config.jwt.presentation.JwtResponse;
import com.yoyomo.global.config.kakao.dto.KakaoAccount;

@Mapper(componentModel = "spring",
	unmappedTargetPolicy = ReportingPolicy.IGNORE,
	nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {

	@Mapping(target = "name", source = "dto.profile.nickname")
	User from(KakaoAccount dto);

	Response toResponse(User user, JwtResponse token);
}
