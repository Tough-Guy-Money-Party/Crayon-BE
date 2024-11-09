package com.yoyomo.domain.mail.domain.entity;

import com.yoyomo.domain.mail.application.converter.LocalDateTimeConverter;
import com.yoyomo.domain.mail.application.dto.MailRequest;
import com.yoyomo.domain.mail.domain.entity.enums.Status;
import lombok.*;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbConvertedBy;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@DynamoDbBean
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Mail {

    private static final String SOURCE_ADDRESS = "mail@crayon.land";

    private String id;
    private String templateId;
    private Map<String, String> customData;
    private String source;
    private String destination;
    private String status;
    private LocalDateTime scheduledTime;

    @DynamoDbPartitionKey
    @DynamoDbAttribute("id")
    public String getId(){
        return id;
    }

    @DynamoDbConvertedBy(LocalDateTimeConverter.class)
    public LocalDateTime getScheduledTime() {
        return scheduledTime;
    }

    public static Mail toMail(MailRequest.Reserve dto, String destination, Map<String, String> customData){
        return Mail.builder()
                .id(UUID.randomUUID().toString())
                .templateId(dto.templateId())
                .customData(customData)
                .destination(destination)
                .source(SOURCE_ADDRESS)
                .scheduledTime(dto.scheduledTime())
                .status(Status.SCHEDULED.toString())
                .build();
    }
}
