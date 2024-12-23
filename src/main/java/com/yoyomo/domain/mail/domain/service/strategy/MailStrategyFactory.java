package com.yoyomo.domain.mail.domain.service.strategy;

import com.yoyomo.domain.mail.exception.MailStrategyMismatchException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class MailStrategyFactory {

    private final Set<MailStrategy> strategies;

    public MailStrategy getStrategy(Type type) {
        return strategies.stream().
                filter(strategy -> strategy.isSupport(type))
                .findFirst()
                .orElseThrow(MailStrategyMismatchException::new);
    }
}
