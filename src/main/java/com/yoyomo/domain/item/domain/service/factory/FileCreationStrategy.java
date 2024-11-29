package com.yoyomo.domain.item.domain.service.factory;

import com.yoyomo.domain.item.application.dto.req.ItemRequest;
import com.yoyomo.domain.item.application.mapper.ItemMapper;
import com.yoyomo.domain.item.domain.entity.Item;
import com.yoyomo.domain.item.domain.entity.Option;
import com.yoyomo.domain.item.domain.entity.Select;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileCreationStrategy {
    private static FileCreationStrategy instance;
    private static ItemMapper itemMapper;

    private FileCreationStrategy(ItemMapper itemMapper) {
        FileCreationStrategy.itemMapper = itemMapper;
    }

    public static FileCreationStrategy getInstance() {
        if (instance == null) {
            return instance = new FileCreationStrategy(itemMapper);
        }
        return instance;
    }

    public Item create(ItemRequest request) {
        List<Option> options = itemMapper.optionRequestToOptionList(request.options());
        return Select.builder()
                .type(request.type())
                .title(request.title())
                .description(request.description())
                .order(request.order())
                .image(request.image())
                .options(options)
                .required(request.required())
                .build();
    }
}
