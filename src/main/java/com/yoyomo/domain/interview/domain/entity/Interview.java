package com.yoyomo.domain.interview.domain.entity;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Interview {
    @Id
    @Builder.Default
    private String id = ObjectId.get().toHexString();

    @NotNull
    private String place;

    @NotNull
    private LocalDateTime date;
}
