package com.yoyomo.domain.recruitment.application.mapper;

import com.yoyomo.domain.recruitment.application.dto.res.ProcessResponse;
import com.yoyomo.domain.recruitment.domain.entity.Process;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProcessMapper {

    @Mappings(
            @Mapping(target = "applicantCount", expression = "java( process.getApplications().size() )")
    )
    ProcessResponse from(Process process);
}

