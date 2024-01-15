package com.yoyomo.domain.form.domain.entity;

import com.yoyomo.domain.item.domain.entity.Item;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "forms")
public class Form {
    @Id
    private String id;
    private String clubId;
    private String name;
    @Builder.Default
    private List<Item> items = new ArrayList<>();

    public void addItem(Item item) {
        this.items.add(item);
    }

    public void removeItem(String id) {
        Item item = items.stream()
                .filter(i -> i.getId().equals(id))
                .findFirst()
                .orElseThrow(NoSuchFieldError::new);
        items.remove(item);
    }
}
