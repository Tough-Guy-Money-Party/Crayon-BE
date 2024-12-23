package com.yoyomo.domain.mail.domain.service;

import com.yoyomo.domain.mail.application.dto.request.MailTransformDto;
import com.yoyomo.domain.mail.application.dto.request.MailUpdateRequest;
import com.yoyomo.domain.mail.application.support.BatchDivider;
import com.yoyomo.domain.mail.domain.entity.Mail;
import com.yoyomo.domain.mail.domain.service.strategy.MailStrategy;
import com.yoyomo.domain.mail.domain.service.strategy.MailStrategyFactory;
import com.yoyomo.domain.mail.domain.service.strategy.Type;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable;
import software.amazon.awssdk.enhanced.dynamodb.Expression;
import software.amazon.awssdk.enhanced.dynamodb.model.PagePublisher;
import software.amazon.awssdk.enhanced.dynamodb.model.ScanEnhancedRequest;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.TransactWriteItem;
import software.amazon.awssdk.services.dynamodb.model.TransactWriteItemsRequest;
import software.amazon.awssdk.services.dynamodb.model.Update;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailUpdateService {

    private static final int BATCH_SIZE = 25;
    private static final String KEY_NAME = "id";

    private final DynamoDbAsyncTable<Mail> mailTable;
    private final DynamoDbAsyncClient dynamoDbAsyncClient;
    private final MailStrategyFactory mailStrategyFactory;
    private final BatchDivider batchDivider;

    public CompletableFuture<Void> update(long processId, MailUpdateRequest dto) {
        return doProcess(processId,Type.UPDATE, dto.scheduledTime());
    }

    public CompletableFuture<Void> cancel(long processId) {
        return doProcess(processId, Type.CANCEL, null);
    }

    public CompletableFuture<Void> doProcess(long processId, Type type, LocalDateTime scheduledTime) {
        ScanEnhancedRequest scanRequest = buildScanRequest(processId);
        MailStrategy strategy = mailStrategyFactory.getStrategy(type);

        PagePublisher<Mail> publisher = mailTable.scan(scanRequest);
        List<Mail> mailsToCancel = new ArrayList<>();

        return publisher
                .subscribe(page -> mailsToCancel.addAll(page.items()))
                .thenCompose(unused -> processInBatches(mailsToCancel, strategy, scheduledTime))
                .thenRun(() -> log.info("[MailUpdateService] 메일 예약 취소 작업 완료"))
                .exceptionally(e -> {
                    log.error("[MailUpdateService] 메일 예약 취소 중 예외 발생", e);
                    throw new CompletionException(e);
                });
    }

    private CompletableFuture<Void> processInBatches(List<Mail> mails, MailStrategy strategy, LocalDateTime scheduledTime) {
        if (mails.isEmpty()) {
            log.info("[MailUpdateService] 취소할 메일이 없습니다.");
            return CompletableFuture.completedFuture(null);
        }

        List<List<Mail>> batches = batchDivider.divide(mails, BATCH_SIZE);

        CompletableFuture<Void> result = CompletableFuture.completedFuture(null);

        for (List<Mail> batch : batches) {
            result = result.thenCompose(unused -> update(batch, strategy, scheduledTime));
        }
        return result;
    }

    private CompletableFuture<Void> update(List<Mail> mails, MailStrategy strategy, LocalDateTime scheduledTime) {
        TransactWriteItemsRequest transactWriteRequest = buildTransactRequest(mails, strategy, scheduledTime);

        return dynamoDbAsyncClient.transactWriteItems(transactWriteRequest)
                .thenAccept(unused -> log.info("[MailUpdateService] 트랜잭션 취소 성공 (배치 크기: {})", mails.size()))
                .exceptionally(e -> {
                    log.error("[MailUpdateService] 트랜잭션 중 예외 발생", e);
                    throw new CompletionException(e);
                });
    }

    private ScanEnhancedRequest buildScanRequest(Long processId) {
        Expression filterExpression = buildExpression(processId);

        return ScanEnhancedRequest.builder()
                .filterExpression(filterExpression)
                .build();
    }

    private Expression buildExpression(Long processId) {
        return Expression.builder()
                .expression("processId = :processId")
                .expressionValues(Map.of(
                        ":processId", AttributeValue.builder().n(processId.toString()).build()
                ))
                .build();
    }

    private TransactWriteItemsRequest buildTransactRequest(List<Mail> mails, MailStrategy strategy, LocalDateTime scheduledTime) {
        List<TransactWriteItem> transactItems = mails.stream()
                .map(mail -> {
                    MailTransformDto dto = MailTransformDto.of(mail, scheduledTime);
                    return buildTransactItem(dto, strategy);
                })
                .collect(Collectors.toList());

        return TransactWriteItemsRequest.builder()
                .transactItems(transactItems)
                .build();
    }

    private TransactWriteItem buildTransactItem(MailTransformDto dto, MailStrategy strategy) {
        strategy.apply(dto);
        Map<String, AttributeValue> key = Map.of(
                KEY_NAME, AttributeValue.builder().s(dto.mail().getId()).build()
        );

        return TransactWriteItem.builder()
                .update(Update.builder()
                        .tableName(mailTable.tableName())
                        .key(key)
                        .updateExpression(strategy.getUpdateExpression())
                        .expressionAttributeNames(strategy.getExpressionAttributeNames())
                        .expressionAttributeValues(strategy.getExpressionValues(dto))
                        .build())
                .build();
    }
}

