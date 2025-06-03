package com.yoyomo.domain.item.domain.service.factory;

import com.yoyomo.domain.application.domain.model.ApplicationReply;
import com.yoyomo.domain.item.application.dto.req.ItemRequest;
import com.yoyomo.domain.item.domain.entity.Item;
import com.yoyomo.domain.item.domain.entity.type.Type;
import com.yoyomo.domain.item.exception.InvalidItemException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class ItemFactory {

    private final Set<ItemCreationStrategy> creationStrategies;
    private final AnswerCreationStrategy answerCreationStrategy;

    public Item createItem(ItemRequest request) {
        Type type = request.type();
        ItemCreationStrategy itemCreationStrategy = creationStrategies.stream()
                .filter(strategy -> strategy.isSupported(type))
                .findFirst()
                .orElseThrow(InvalidItemException::new);
        return itemCreationStrategy.create(request);
    }

    public List<Item> createItem(ApplicationReply applicationReply) {
        return answerCreationStrategy.create(applicationReply);
    }
}
