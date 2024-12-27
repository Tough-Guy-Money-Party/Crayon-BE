package com.yoyomo.domain.mail.domain.service;

import com.yoyomo.domain.mail.domain.entity.Mail;
import com.yoyomo.fixture.TestFixture;
import com.yoyomo.global.common.util.BatchDivider;
import com.yoyomo.infra.aws.dynamodb.config.AwsDynamodbConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;

import java.util.List;
import java.util.concurrent.CompletionException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest(classes = {AwsDynamodbConfig.class, MailSaveService.class, BatchDivider.class})
public class MailSaveServiceTest {
    @Autowired
    private MailSaveService mailSaveService;

    @Autowired
    private DynamoDbAsyncTable<Mail> mailTable;

    @Autowired
    BatchDivider batchDivider;

    @Autowired
    DynamoDbEnhancedAsyncClient dynamoDbClient;

    @DisplayName("메일 업로드시 트랜잭션 테스트")
    @Test
    void upload() {
        // given
        Mail validMail = TestFixture.mail();
        Mail invalidMail = TestFixture.invalidMail();

        List<Mail> mails = List.of(validMail, invalidMail);
        // when & then
        assertThatThrownBy(() -> mailSaveService.upload(mails).join())
                .isInstanceOf(CompletionException.class);
    }
}
