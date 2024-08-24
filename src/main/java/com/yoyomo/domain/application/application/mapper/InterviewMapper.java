package com.yoyomo.domain.application.application.mapper;

import com.yoyomo.domain.application.application.dto.request.InterviewRequestDTO.Save;
import com.yoyomo.domain.application.domain.entity.Interview;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface InterviewMapper {

    Interview from(Save dto);
}
