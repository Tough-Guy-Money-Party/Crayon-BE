package com.yoyomo.domain.mail.domain.service;

import com.yoyomo.domain.mail.application.dto.request.MailTransformDto;
import com.yoyomo.domain.mail.application.dto.request.MailUpdateRequest;
import com.yoyomo.domain.mail.domain.entity.Mail;
import com.yoyomo.domain.mail.domain.service.strategy.MailStrategy;
import com.yoyomo.domain.mail.domain.service.strategy.MailStrategyFactory;
import com.yoyomo.domain.mail.domain.service.strategy.Type;
import com.yoyomo.global.common.util.BatchDivider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.enhanced.dynamodb.Expression;
import software.amazon.awssdk.enhanced.dynamodb.model.PagePublisher;
import software.amazon.awssdk.enhanced.dynamodb.model.ScanEnhancedRequest;
import software.amazon.awssdk.enhanced.dynamodb.model.TransactUpdateItemEnhancedRequest;
import software.amazon.awssdk.enhanced.dynamodb.model.TransactWriteItemsEnhancedRequest;
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

    private static final int BATCH_SIZE = 50;

    private final DynamoDbAsyncTable<Mail> mailTable;
    private final DynamoDbEnhancedAsyncClient dynamoDbEnhancedAsyncClient;
    private final MailStrategyFactory mailStrategyFactory;
    private final BatchDivider batchDivider;

    public CompletableFuture<Void> updateScheduledTime(long processId, MailUpdateRequest dto) {
        MailStrategy strategy = mailStrategyFactory.getStrategy(Type.UPDATE);
        strategy.setScheduledTime(dto.scheduledTime());

        return processMailOperation(processId, strategy);
    }

    public CompletableFuture<Void> cancelMail(long processId) {
        MailStrategy strategy = mailStrategyFactory.getStrategy(Type.CANCEL);

        return processMailOperation(processId, strategy);
    }

    public CompletableFuture<Void> processMailOperation(long processId, MailStrategy strategy) {
        ScanEnhancedRequest scanRequest = buildScanRequest(processId);

        PagePublisher<Mail> publisher = mailTable.scan(scanRequest);
        List<Mail> mailsToCancel = new ArrayList<>();

        return publisher
                .subscribe(page -> mailsToCancel.addAll(page.items()))
                .thenCompose(unused -> processMailsInBatches(mailsToCancel, strategy))
                .thenRun(() -> log.info("[MailUpdateService] 메일 예약 수정/취소 작업 완료"))
                .exceptionally(e -> {
                    log.error("[MailUpdateService] 메일 예약 수정/취소 중 예외 발생", e);
                    throw new CompletionException(e);
                });
    }

    private CompletableFuture<Void> processMailsInBatches(List<Mail> mails, MailStrategy strategy) {
        if (mails.isEmpty()) {
            log.info("[MailUpdateService] 수정/취소 메일이 없습니다.");
            return CompletableFuture.completedFuture(null);
        }

        List<List<Mail>> batches = batchDivider.divide(mails, BATCH_SIZE);

        CompletableFuture<Void> result = CompletableFuture.completedFuture(null);

        for (List<Mail> batch : batches) {
            result = result.thenCompose(unused -> executeBatchUpdate(batch, strategy));
        }
        return result;
    }

    private CompletableFuture<Void> executeBatchUpdate(List<Mail> mails, MailStrategy strategy) {
        TransactWriteItemsEnhancedRequest transactWriteRequest = buildTransactRequest(mails, strategy);

        return dynamoDbEnhancedAsyncClient.transactWriteItems(transactWriteRequest)
                .thenAccept(unused -> log.info("[MailUpdateService] 트랜잭션 수정/취소 성공 (배치 크기: {})", mails.size()))
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

    private TransactWriteItemsEnhancedRequest buildTransactRequest(List<Mail> mails, MailStrategy strategy) {
        TransactWriteItemsEnhancedRequest.Builder requestBuilder = TransactWriteItemsEnhancedRequest.builder();

        mails.forEach(mail -> {
            MailTransformDto dto = MailTransformDto.of(mail, strategy.getScheduledTime());
            strategy.apply(dto);

            TransactUpdateItemEnhancedRequest<Mail> updateRequest = TransactUpdateItemEnhancedRequest.builder(Mail.class)
                    .item(mail)
                    .build();

            requestBuilder.addUpdateItem(mailTable, updateRequest);
        });

        return requestBuilder.build();
    }
}

