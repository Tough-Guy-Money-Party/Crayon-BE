package com.yoyomo.domain.mail.domain.service;

import com.yoyomo.domain.mail.application.dto.request.MailCancelRequest;
import com.yoyomo.domain.mail.domain.entity.Mail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable;
import software.amazon.awssdk.enhanced.dynamodb.Expression;
import software.amazon.awssdk.enhanced.dynamodb.model.PagePublisher;
import software.amazon.awssdk.enhanced.dynamodb.model.ScanEnhancedRequest;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailUpdateService {

    private final DynamoDbAsyncTable<Mail> mailTable;

    public CompletableFuture<Void> cancelMail(MailCancelRequest dto) {
        Expression filterExpression = Expression.builder()
                .expression("processId = :processId")
                .expressionValues(Map.of(
                        ":processId", AttributeValue.builder().n(dto.processId().toString()).build()
                ))
                .build();

        ScanEnhancedRequest scanRequest = ScanEnhancedRequest.builder()
                .filterExpression(filterExpression)
                .build();

        PagePublisher<Mail> publisher = mailTable.scan(scanRequest);
        List<CompletableFuture<Void>> updateFutures = new ArrayList<>();

        return publisher
                .subscribe(page -> {
                    page.items().forEach(mail -> {
                        CompletableFuture<Void> future = updateMailStatus(mail);
                        updateFutures.add(future);
                    });
                })
                .thenCompose(unused -> {
                    return CompletableFuture.allOf(updateFutures.toArray(new CompletableFuture[0]));
                })
                .thenRun(() -> log.info("[MailUpdateService] 메일 예약 취소 작업 완료"))
                .exceptionally(e -> {
                    log.error("[MailUpdateService] 메일 예약 취소 중 예외 발생", e);
                    throw new CompletionException(e);
                });
    }

    private CompletableFuture<Void> updateMailStatus(Mail mail) {
        mail.cancel();
        return mailTable.updateItem(mail)
                .thenAccept(unused -> {})
                .exceptionally(e -> {
                    log.error("[MailUpdateService] 메일 ID={} 예약 취소 중 예외 발생", mail.getId(), e);
                    throw new CompletionException(e);
                });
    }
}

