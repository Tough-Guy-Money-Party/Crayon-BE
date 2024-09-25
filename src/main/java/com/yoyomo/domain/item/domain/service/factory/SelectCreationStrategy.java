package com.yoyomo.domain.item.domain.service.factory;

import com.yoyomo.domain.item.application.dto.req.ItemRequest;
import com.yoyomo.domain.item.application.mapper.ItemMapper;
import com.yoyomo.domain.item.domain.entity.Item;
import com.yoyomo.domain.item.domain.entity.Option;
import com.yoyomo.domain.item.domain.entity.Select;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SelectCreationStrategy implements ItemCreationStrategy {
    private static SelectCreationStrategy instance;
    private static ItemMapper itemMapper;

    private SelectCreationStrategy(ItemMapper itemMapper) {
        SelectCreationStrategy.itemMapper = itemMapper;
    }

    public static SelectCreationStrategy getInstance() {
        if (instance == null) {
            return instance = new SelectCreationStrategy(itemMapper);
        }
        return instance;
    }

    @Override
    public Item create(ItemRequest request) {
        List<Option> options = itemMapper.optionRequestToOptionList(request.options());
        return Select.builder()
                .type(request.type())
                .title(request.title())
                .description(request.description())
                .order(request.order())
                .image(request.image())
                .required(request.required())
                .options(options)
                .build();
    }
}
