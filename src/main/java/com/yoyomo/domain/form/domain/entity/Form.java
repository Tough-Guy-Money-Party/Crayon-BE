package com.yoyomo.domain.form.domain.entity;

import com.yoyomo.domain.item.domain.entity.Item;
import com.yoyomo.domain.item.exception.ItemNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

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
    private Map<String, Item> items = new LinkedHashMap<>();

    public Item getItem(String itemId) {
        return Optional.ofNullable(items.get(itemId))
                .orElseThrow(ItemNotFoundException::new);
    }

    public void addItem(Item item) {
        this.items.put(ObjectId.get().toHexString(), item);
    }

    public void updateItem(String itemId, Item item) {
        Optional.ofNullable(items.replace(itemId, item))
                .orElseThrow(ItemNotFoundException::new);
    }

    public void removeItem(String itemId) {
        Optional.ofNullable(items.remove(itemId))
                .orElseThrow(ItemNotFoundException::new);
    }
}
