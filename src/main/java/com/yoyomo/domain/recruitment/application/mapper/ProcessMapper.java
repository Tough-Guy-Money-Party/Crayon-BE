package com.yoyomo.domain.recruitment.application.mapper;

import com.yoyomo.domain.recruitment.application.dto.res.ProcessResponse;
import com.yoyomo.domain.recruitment.domain.entity.Process;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProcessMapper {

    ProcessResponse from(Process process);
}

