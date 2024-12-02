package com.yoyomo.domain.user.application.mapper;

import com.yoyomo.domain.user.application.dto.response.UserResponseDTO.ManagerInfo;
import com.yoyomo.domain.user.domain.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ManagerMapper {
    ManagerInfo toManagerInfoDTO(User user);
}
