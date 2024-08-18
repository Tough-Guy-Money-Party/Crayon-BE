package com.yoyomo.domain.process.application.mapper;

import com.yoyomo.domain.process.application.dto.request.ProcessRequestDTO.Save;
import com.yoyomo.domain.process.domain.entity.Process;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProcessMapper {

    List<Process> fromAll(List<Save> dto);
}
