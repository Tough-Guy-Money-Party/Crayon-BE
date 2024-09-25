package com.yoyomo.domain.item.domain.service.factory;

import com.yoyomo.domain.item.application.dto.req.ItemRequest;
import com.yoyomo.domain.item.domain.entity.Item;
import com.yoyomo.domain.item.domain.entity.Score;
import org.springframework.stereotype.Service;

@Service
public class ScoreCreationStrategy implements ItemCreationStrategy {
    private static ScoreCreationStrategy instance;

    private ScoreCreationStrategy() {
    }

    public static ScoreCreationStrategy getInstance() {
        if (instance == null) {
            return instance = new ScoreCreationStrategy();
        }
        return instance;
    }

    @Override
    public Item create(ItemRequest request) {
        return Score.builder()
                .type(request.type())
                .title(request.title())
                .description(request.description())
                .order(request.order())
                .image(request.image())
                .required(request.required())
                .meaningOfHigh(request.meaningOfHigh())
                .meaningOfLow(request.meaningOfLow())
                .score(request.score())
                .build();
    }
}
