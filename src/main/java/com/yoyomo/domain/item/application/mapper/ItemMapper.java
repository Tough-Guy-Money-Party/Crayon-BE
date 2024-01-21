package com.yoyomo.domain.item.application.mapper;

import com.yoyomo.domain.item.application.dto.res.SelectResponse;
import com.yoyomo.domain.item.application.dto.res.TextResponse;
import com.yoyomo.domain.item.domain.entity.Select;
import com.yoyomo.domain.item.domain.entity.Text;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ItemMapper {
    TextResponse mapToTextResponse(Text text);

    SelectResponse mapToSelectResponse(Select select);

}
