package com.yoyomo.domain.form.domain.entity;

import com.yoyomo.domain.item.domain.entity.Item;
import com.yoyomo.global.common.entity.BaseEntity;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "forms")
public class Form extends BaseEntity {

    @Id
    private String id;

    private String clubId;

    private String title;

    private String description;

    private List<Item> items = new ArrayList<>();

    private boolean enabled;

    private LocalDateTime deletedAt;

    public void updateItems(List<Item> items) {
        this.items = items;
    }

    @PrePersist
    public void init() {
        enabled = true;
        deletedAt = null;
    }
}
