package com.yoyomo.domain.item.application.mapper;

import com.yoyomo.domain.item.application.dto.res.SelectResponse;
import com.yoyomo.domain.item.domain.entity.Item;
import com.yoyomo.domain.item.domain.entity.Select;
import com.yoyomo.domain.item.domain.entity.type.Type;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SelectResponseCreationStrategy implements ItemResponseCreationStrategy {

    @Override
    public boolean isSupported(Type type) {
        return Type.SELECT == type || Type.MULTI_SELECT == type;
    }

    @Override
    public SelectResponse create(Item item) {
        return SelectResponse.toResponse((Select) item);
    }
}
