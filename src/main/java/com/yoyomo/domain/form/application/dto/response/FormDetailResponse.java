package com.yoyomo.domain.form.application.dto.response;

import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.item.application.dto.res.ItemResponse;
import com.yoyomo.domain.item.domain.entity.Item;
import lombok.Builder;

import java.util.List;

@Builder
public record FormDetailResponse(
        String clubName,
        List<ItemResponse> items
) {

    public static FormDetailResponse toResponse(Club clubName, List<Item> items) {
        List<ItemResponse> itemResponses = items.stream()
                .map(ItemResponse::toResponse)
                .toList();

        return FormDetailResponse.builder()
                .clubName(clubName.getName())
                .items(itemResponses)
                .build();
    }
}
