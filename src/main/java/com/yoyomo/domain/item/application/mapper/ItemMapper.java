package com.yoyomo.domain.item.application.mapper;

import com.yoyomo.domain.item.application.dto.res.*;
import com.yoyomo.domain.item.domain.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ItemMapper {
    TextResponse mapToTextResponse(Text text);

    SelectResponse mapToSelectResponse(Select select);

    StageResponse mapToStageResponse(Stage stage);

    DateResponse mapToDateResponse(Date date);

    FileResponse mapToFileResponse(File file);



}
