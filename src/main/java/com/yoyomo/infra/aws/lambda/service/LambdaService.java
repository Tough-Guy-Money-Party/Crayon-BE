package com.yoyomo.infra.aws.lambda.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.lambda.LambdaAsyncClient;
import software.amazon.awssdk.services.lambda.model.InvocationType;
import software.amazon.awssdk.services.lambda.model.InvokeRequest;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

@Slf4j
@Service
@RequiredArgsConstructor
public class LambdaService {

    private final LambdaAsyncClient lambdaAsyncClient;

    public CompletableFuture<Void> invokeLambdaAsync(String functionName) {
        InvokeRequest request = InvokeRequest.builder()
                .functionName(functionName)
                .invocationType(InvocationType.EVENT)
                .build();

        return lambdaAsyncClient.invoke(request)
                .thenAccept(response -> log.info("[LambdaService] 람다 호출 성공"))
                .exceptionally(e -> {
                    log.error("[LambdaService] 람다 호출 중 에러 발생");
                    throw new CompletionException(e);
                });
    }
}
