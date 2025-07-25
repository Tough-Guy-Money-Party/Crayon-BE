package com.yoyomo.domain.user.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.yoyomo.domain.user.application.dto.response.UserResponse.ManagerInfo;
import com.yoyomo.domain.user.domain.entity.User;

@Mapper(componentModel = "spring",
	unmappedTargetPolicy = ReportingPolicy.IGNORE,
	nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ManagerMapper {
	ManagerInfo toManagerInfo(User user);
}
