package com.yoyomo.domain.item.application.dto.res;

import com.yoyomo.domain.item.domain.entity.Item;
import com.yoyomo.domain.item.domain.entity.type.Type;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class ItemResponse {
    private String id;
    private String title;
    private String description;
    private Type type;
    private int order;
    private String imageName;
    private boolean required;

    public static List<ItemResponse> itemListToItemResponseList(List<Item> list) {
        if (list == null) {
            return null;
        }

        List<ItemResponse> list1 = new ArrayList<ItemResponse>(list.size());
        for (Item item : list) {
            list1.add(itemToItemResponse(item));
        }

        return list1;
    }

    private static ItemResponse itemToItemResponse(Item item) {
        if (item == null) {
            return null;
        }

        ItemResponse.ItemResponseBuilder<?, ?> itemResponse = ItemResponse.builder();

        itemResponse.id(item.getId());
        itemResponse.title(item.getTitle());
        itemResponse.description(item.getDescription());
        itemResponse.type(item.getType());
        itemResponse.order(item.getOrder());
        itemResponse.required(item.isRequired());

        return itemResponse.build();
    }
}
