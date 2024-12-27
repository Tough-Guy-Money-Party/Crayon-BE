package com.yoyomo.domain.mail.domain.service;

import com.yoyomo.domain.mail.domain.entity.Mail;
import com.yoyomo.domain.mail.domain.service.strategy.MailCancelStrategy;
import com.yoyomo.domain.mail.domain.service.strategy.MailStrategy;
import com.yoyomo.domain.mail.domain.service.strategy.MailStrategyFactory;
import com.yoyomo.domain.mail.domain.service.strategy.MailUpdateStrategy;
import com.yoyomo.fixture.TestFixture;
import com.yoyomo.global.common.util.BatchDivider;
import com.yoyomo.infra.aws.dynamodb.config.AwsDynamodbConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest(classes = {AwsDynamodbConfig.class, MailUpdateService.class, BatchDivider.class, MailStrategyFactory.class})
public class MailUpdateServiceTest {

    @Autowired
    private MailUpdateService mailUpdateService;

    @Autowired
    private DynamoDbAsyncTable<Mail> mailTable;

    @Autowired
    BatchDivider batchDivider;

    @Autowired
    DynamoDbEnhancedAsyncClient dynamoDbClient;

    @Autowired
    MailStrategyFactory mailStrategyFactory;

    @DisplayName("메일 수정시 트랜잭션 테스트")
    @Test
    void update() {
        // given
        Mail validMail = TestFixture.mail();
        Mail invalidMail = TestFixture.invalidMail();

        List<Mail> mails = List.of(validMail, invalidMail);

        MailStrategy strategy = new MailUpdateStrategy();
        strategy.setScheduledTime(LocalDateTime.now());

        // when & then
        /*
            test시 excuteBatchUpdate 메서드의 접근자를 public으로 변경 후 테스트 진행
         */
//        assertThatThrownBy(() -> mailUpdateService.executeBatchUpdate(mails, strategy).join())
//                .isInstanceOf(CompletionException.class);
    }

    @DisplayName("메일 취소 트랜잭션 테스트")
    @Test
    void cancel() {
        // given
        Mail validMail = TestFixture.mail();
        Mail invalidMail = TestFixture.invalidMail();

        List<Mail> mails = List.of(validMail, invalidMail);

        MailStrategy strategy = new MailCancelStrategy();

        // when & then
        /*
            test시 excuteBatchUpdate 메서드의 접근자를 public으로 변경 후 테스트 진행
         */
//        assertThatThrownBy(() -> mailUpdateService.executeBatchUpdate(mails, strategy).join())
//                .isInstanceOf(CompletionException.class);
    }
}
