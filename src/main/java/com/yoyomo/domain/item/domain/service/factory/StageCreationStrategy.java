package com.yoyomo.domain.item.domain.service.factory;

import com.yoyomo.domain.item.application.dto.req.ItemRequest;
import com.yoyomo.domain.item.domain.entity.Item;
import com.yoyomo.domain.item.domain.entity.Select;
import com.yoyomo.domain.item.domain.entity.Stage;
import org.springframework.stereotype.Service;

@Service
public class StageCreationStrategy implements ItemCreationStrategy {
    private static StageCreationStrategy instance;

    private StageCreationStrategy() {
    }

    public static StageCreationStrategy getInstance() {
        if (instance == null) {
            return instance = new StageCreationStrategy();
        }
        return instance;
    }

    @Override
    public Item create(ItemRequest request) {
        return Stage.builder()
                .type(request.type())
                .title(request.title())
                .description(request.description())
                .order(request.order())
                .required(request.required())
                .meaningOfHigh(request.meaningOfHigh())
                .meaningOfLow(request.meaningOfLow())
                .build();
    }
}
