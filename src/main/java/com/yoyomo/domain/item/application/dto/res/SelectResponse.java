package com.yoyomo.domain.item.application.dto.res;

import com.yoyomo.domain.item.domain.entity.Select;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@SuperBuilder
public class SelectResponse extends ItemResponse {
    private List<OptionResponse> options;

    public static SelectResponse toResponse(Select select) {
        List<OptionResponse> options = select.getOptions().stream()
                .map(OptionResponse::toResponse)
                .toList();

        return SelectResponse.builder()
                .id(select.getId())
                .title(select.getTitle())
                .description(select.getDescription())
                .type(select.getType())
                .order(select.getOrder())
                .imageName(select.getImage().getName())
                .required(select.isRequired())
                .options(options)
                .build();
    }
}
