package com.yoyomo.global.common;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Supplier;

@Component
public class TransactionSupporter {

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public <T> T executeNewTransaction(Supplier<T> method) {
        return method.get();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void executeNewTransaction(Runnable runnable) {
        runnable.run();
    }
}
