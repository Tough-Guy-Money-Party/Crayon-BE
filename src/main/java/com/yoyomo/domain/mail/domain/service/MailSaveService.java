package com.yoyomo.domain.mail.domain.service;

import com.yoyomo.domain.mail.domain.entity.Mail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.model.BatchWriteItemEnhancedRequest;
import software.amazon.awssdk.enhanced.dynamodb.model.WriteBatch;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailSaveService {

    private static final int BATCH_SIZE = 25;

    private final DynamoDbEnhancedAsyncClient dynamoDbEnhancedAsyncClient;
    private final DynamoDbAsyncTable<Mail> mailTable;

    public CompletableFuture<Void> upload(List<Mail> mails) {
        List<List<Mail>> batches = partitionList(mails);
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
        WriteBatch.Builder<Mail> writeBatchBuilder = WriteBatch.builder(Mail.class)
                .mappedTableResource(mailTable);

        for (Mail mail : batch) {
            writeBatchBuilder.addPutItem(mail);
        }

        BatchWriteItemEnhancedRequest batchWriteItemEnhancedRequest = BatchWriteItemEnhancedRequest.builder()
                .addWriteBatch(writeBatchBuilder.build())
                .build();

        return dynamoDbEnhancedAsyncClient.batchWriteItem(batchWriteItemEnhancedRequest)
                .thenAccept(result -> log.info("[MailSaveService] | {} 개의 메일 배치 업로드 성공", batch.size()))
                .exceptionally(ex -> {
                    log.error("[MailSaveService] | 배치 업로드 중 예외 발생: {}", ex.getMessage(), ex);
                    throw new CompletionException(ex);
                });
    }

    private <T> List<List<T>> partitionList(List<T> list) {
        return IntStream.range(0, (list.size() + BATCH_SIZE - 1) / BATCH_SIZE)
                .mapToObj(i -> list.subList(i * BATCH_SIZE, Math.min((i + 1) * BATCH_SIZE, list.size())))
                .collect(Collectors.toList());
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
