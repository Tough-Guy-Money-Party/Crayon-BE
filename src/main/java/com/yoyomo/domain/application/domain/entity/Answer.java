package com.yoyomo.domain.application.domain.entity;

import com.yoyomo.domain.item.domain.entity.Item;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "answers")
public class Answer {

    @Id
    private String id;

    private UUID applicationId;

    private List<Item> items;

    public void update(List<Item> items) {
        this.items = items;
    }
}
