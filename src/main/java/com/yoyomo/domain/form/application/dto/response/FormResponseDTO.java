package com.yoyomo.domain.form.application.dto.response;

import com.yoyomo.domain.form.domain.entity.Form;
import com.yoyomo.domain.form.domain.repository.dto.LinkedRecruitment;
import com.yoyomo.domain.item.application.dto.res.ItemResponse;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class FormResponseDTO {

    public record SaveResponse(
            String id
    ) {
    }

    @Builder
    public record DetailResponse(
            String title,
            String description,
            List<ItemResponse> items,
            List<String> recruitmentIds,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {

        public static DetailResponse toResponse(Form form, List<ItemResponse> items, List<String> recruitmentIds) {
            return DetailResponse.builder()
                    .title(form.getTitle())
                    .description(form.getDescription())
                    .items(items)
                    .recruitmentIds(recruitmentIds)
                    .createdAt(form.getCreatedAt())
                    .updatedAt(form.getUpdatedAt())
                    .build();
        }
    }

    public record Info(
            String title,
            String description,
            List<ItemResponse> items,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        public static Info toInfo(Optional<Form> wrappedForm) {
            if (wrappedForm.isEmpty()) {
                return null;
            }

            Form form = wrappedForm.get();

            List<ItemResponse> itemResponses = ItemResponse.itemListToItemResponseList(form.getItems());

            return new Info(
                    form.getTitle(),
                    form.getDescription(),
                    itemResponses,
                    form.getCreatedAt(),
                    form.getUpdatedAt()
            );
        }

    }

    @Builder
    public record Response(
            String id,
            String title,
            String description,
            List<UUID> recruitmentIds,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {

        public static Response toResponse(Form form, List<LinkedRecruitment> linkedRecruitments) {
            List<UUID> recruitmentIds = linkedRecruitments.stream()
                    .map(LinkedRecruitment::recruitmentId)
                    .toList();

            return Response.builder()
                    .id(form.getId())
                    .title(form.getTitle())
                    .description(form.getDescription())
                    .recruitmentIds(recruitmentIds)
                    .createdAt(form.getCreatedAt())
                    .updatedAt(form.getUpdatedAt())
                    .build(); //todo createdAt, updatedAt 추가
        }
    }
}
