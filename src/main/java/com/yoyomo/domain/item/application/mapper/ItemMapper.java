package com.yoyomo.domain.item.application.mapper;

import com.yoyomo.domain.item.application.dto.req.OptionRequest;
import com.yoyomo.domain.item.application.dto.res.*;
import com.yoyomo.domain.item.domain.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ItemMapper {
    TextResponse mapToTextResponse(Text text);

    SelectResponse mapToSelectResponse(Select select);

    StageResponse mapToStageResponse(Stage stage);

    DateResponse mapToDateResponse(Date date);

    FileResponse mapToFileResponse(File file);

    default List<OptionResponse> optionsToOptionResponseList(List<Option> options) {
        return options.stream()
                .map(option -> new OptionResponse(option.getId(), option.getTitle(), option.isSelected()))
                .collect(Collectors.toList());
    }

    default List<Option> optionRequestToOptionList(List<OptionRequest> optionRequests) {
        return optionRequests.stream()
                .map(optionRequest -> new Option(optionRequest.title(), optionRequest.selected()))
                .collect(Collectors.toList());
    }

}
