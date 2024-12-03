package com.yoyomo.domain.form.domain.entity;

import com.yoyomo.domain.item.domain.entity.Item;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "forms")
public class Form {

    @Id
    private String id;

    private String clubId;

    private String title;

    private String description;

    private List<Item> items = new ArrayList<>();

    @CreatedDate
    private LocalDateTime createdAt;

    private LocalDateTime deletedAt;

    public void update(String title, String description, List<Item> items) {
        this.title = title;
        this.description = description;
        this.items = items;
    }
}
