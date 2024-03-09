package com.yoyomo.domain.interview.application.mapper;

import com.yoyomo.domain.interview.application.dto.InterviewRequest;
import com.yoyomo.domain.interview.domain.entity.Interview;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface InterviewMapper {
    Interview from(InterviewRequest request);
}
