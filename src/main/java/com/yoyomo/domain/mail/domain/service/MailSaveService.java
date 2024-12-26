package com.yoyomo.domain.mail.domain.service;

import com.yoyomo.domain.mail.domain.entity.Mail;
import com.yoyomo.global.common.util.BatchDivider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.model.TransactWriteItemsEnhancedRequest;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailSaveService {

    private static final int BATCH_SIZE = 50;

    private final BatchDivider batchDivider;
    private final DynamoDbEnhancedAsyncClient dynamoDbEnhancedAsyncClient;
    private final DynamoDbAsyncTable<Mail> mailTable;

    public CompletableFuture<Void> upload(List<Mail> mails) {
        List<List<Mail>> batches = batchDivider.divide(mails, BATCH_SIZE);
        log.info("[MailSaveService] | Divided mails into {} batches.", batches.size());

        List<CompletableFuture<Void>> futures = batches.stream()
                .map(this::batchWrite)
                .toList();

        return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                .thenRun(() -> log.info("[MailSaveService] | {} 개의 메일 비동기 업로드 성공", mails.size()))
                .exceptionally(ex->{
                    log.error("[MailSaveService] | 비동기 업로드 중 예외 발생: {}", ex.getMessage(), ex);
                    throw new CompletionException(ex);
                        });
    }

    private CompletableFuture<Void> batchWrite(List<Mail> batch) {
        TransactWriteItemsEnhancedRequest.Builder requestBuilder = TransactWriteItemsEnhancedRequest.builder();

        for (Mail mail : batch) {
            requestBuilder.addPutItem(mailTable, mail);
        }

        TransactWriteItemsEnhancedRequest request = requestBuilder.build();

        return dynamoDbEnhancedAsyncClient.transactWriteItems(request)
                .thenAccept(result -> log.info("[MailSaveService] | {} 개의 메일 트랜잭션 업로드 성공", batch.size()))
                .exceptionally(ex -> {
                    log.error("[MailSaveService] | 배치 업로드 중 예외 발생: {}", ex.getMessage(), ex);
                    throw new CompletionException(ex);
                });
    }

    /*
        * 메일 단건 조작 관련 메서드
        * 차후 사용할 수도 있을 것 같아 냅둠
     */
    public void save(Mail mail){
        try {
            mailTable.putItem(mail);
            log.info("Saved mail with ID: {}", mail.getId());
        } catch (DynamoDbException e) {
            log.error("Failed to save mail ID {}: {}", mail.getId(), e.getMessage());
        }
    }

    public void update(Mail mail){
        try {
            mailTable.putItem(mail);
            log.info("Updated mail with ID: {}", mail.getId());
        } catch (DynamoDbException e) {
            log.error("Failed to update mail ID {}: {}", mail.getId(), e.getMessage());
        }
    }

    public void delete(Mail mail){
        try {
            mailTable.deleteItem(mail);
            log.info("Deleted mail with ID: {}", mail.getId());
        } catch (DynamoDbException e) {
            log.error("Failed to delete mail ID {}: {}", mail.getId(), e.getMessage());
        }
    }

    public CompletableFuture<Mail> findById(String id) { // ID를 String으로 수정
        try {
            return mailTable.getItem(Key.builder().partitionValue(id).build());
        } catch (DynamoDbException e) {
            log.error("Failed to find mail ID {}: {}", id, e.getMessage());
            return null;
        }
    }

}
