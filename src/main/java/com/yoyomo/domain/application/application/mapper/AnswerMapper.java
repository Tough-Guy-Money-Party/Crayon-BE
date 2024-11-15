package com.yoyomo.domain.application.application.mapper;

import com.yoyomo.domain.application.domain.entity.Answer;
import com.yoyomo.domain.item.domain.entity.Item;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)

public interface AnswerMapper {

    @Mapping(target = "id", ignore = true)
    Answer from(List<Item> items, UUID applicationId);
}
