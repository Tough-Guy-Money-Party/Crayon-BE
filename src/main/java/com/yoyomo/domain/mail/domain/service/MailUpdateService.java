package com.yoyomo.domain.mail.domain.service;

import com.yoyomo.domain.mail.domain.entity.Mail;
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
    private static final String ATTRIBUTE_NAME = "status";

    private final DynamoDbAsyncTable<Mail> mailTable;
    private final DynamoDbAsyncClient dynamoDbAsyncClient;

    public CompletableFuture<Void> cancelMail(Long processId) {
        ScanEnhancedRequest scanRequest = buildScanRequest(processId);

        PagePublisher<Mail> publisher = mailTable.scan(scanRequest);
        List<Mail> mailsToCancel = new ArrayList<>();

        return publisher
                .subscribe(page -> mailsToCancel.addAll(page.items()))
                .thenCompose(unused -> processInBatches(mailsToCancel))
                .thenRun(() -> log.info("[MailUpdateService] 메일 예약 취소 작업 완료"))
                .exceptionally(e -> {
                    log.error("[MailUpdateService] 메일 예약 취소 중 예외 발생", e);
                    throw new CompletionException(e);
                });
    }

    private CompletableFuture<Void> processInBatches(List<Mail> mails) {
        if (mails.isEmpty()) {
            log.info("[MailUpdateService] 취소할 메일이 없습니다.");
            return CompletableFuture.completedFuture(null);
        }

        List<List<Mail>> batches = divide(mails, BATCH_SIZE);

        CompletableFuture<Void> result = CompletableFuture.completedFuture(null);

        for (List<Mail> batch : batches) {
            result = result.thenCompose(unused -> update(batch));
        }
        return result;
    }

    private CompletableFuture<Void> update(List<Mail> mails) {
        TransactWriteItemsRequest transactWriteRequest = buildTransactRequest(mails);

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

    private List<List<Mail>> divide(List<Mail> mails, int batchSize) {
        List<List<Mail>> batches = new ArrayList<>();
        for (int i = 0; i < mails.size(); i += batchSize) {
            batches.add(mails.subList(i, Math.min(i + batchSize, mails.size())));
        }
        return batches;
    }

    private TransactWriteItemsRequest buildTransactRequest(List<Mail> mails) {
        List<TransactWriteItem> transactItems = mails.stream()
                .map(this::buildTransactItem)
                .collect(Collectors.toList());

        return TransactWriteItemsRequest.builder()
                .transactItems(transactItems)
                .build();
    }

    private TransactWriteItem buildTransactItem(Mail mail) {
        mail.cancel();
        Map<String, AttributeValue> key = Map.of(
                KEY_NAME, AttributeValue.builder().s(mail.getId()).build()
        );

        return TransactWriteItem.builder()
                .update(Update.builder()
                        .tableName(mailTable.tableName())
                        .key(key)
                        .updateExpression("SET #status = :status")
                        .expressionAttributeNames(Map.of("#status", ATTRIBUTE_NAME))
                        .expressionAttributeValues(Map.of(
                                ":status", AttributeValue.builder().s(String.valueOf(mail.getStatus())).build()
                        ))
                        .build())
                .build();
    }
}

