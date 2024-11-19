package com.yoyomo.domain.item.domain.service.factory;

import com.yoyomo.domain.item.application.dto.req.ItemRequest;
import com.yoyomo.domain.item.application.dto.req.OptionRequest;
import com.yoyomo.domain.item.domain.entity.Item;
import com.yoyomo.domain.item.domain.entity.Option;
import com.yoyomo.domain.item.domain.entity.Select;
import com.yoyomo.domain.item.domain.entity.type.Type;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SelectCreationStrategy implements ItemCreationStrategy {

    @Override
    public boolean isSupported(Type type) {
        return Type.SELECT == type || Type.MULTI_SELECT == type;
    }

    @Override
    public Item create(ItemRequest request) {
        List<Option> options = request.options().stream()
                .map(OptionRequest::toOption)
                .toList();

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
