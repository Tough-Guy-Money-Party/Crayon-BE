package com.yoyomo.domain.application.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.yoyomo.domain.application.application.dto.request.InterviewRequest.Save;
import com.yoyomo.domain.application.domain.entity.Interview;

@Mapper(componentModel = "spring",
	unmappedTargetPolicy = ReportingPolicy.IGNORE,
	nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface InterviewMapper {

	Interview from(Save dto);
}
