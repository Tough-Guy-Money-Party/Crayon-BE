package com.yoyomo.domain.mail.domain.entity;

import java.time.LocalDateTime;
import java.util.Map;

import com.yoyomo.domain.mail.application.converter.LocalDateTimeConverter;
import com.yoyomo.domain.mail.domain.entity.enums.CustomType;
import com.yoyomo.domain.mail.domain.entity.enums.EnumMapAttributeConverter;
import com.yoyomo.domain.mail.domain.entity.enums.Status;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbConvertedBy;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@DynamoDbBean
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Mail {

	private String id;
	private String templateId;
	private Map<CustomType, String> customData;
	private String source;
	private String destination;
	private Status status;
	private LocalDateTime scheduledTime;
	private Long ttl;
	private Long processId;

	@DynamoDbPartitionKey
	@DynamoDbAttribute("id")
	public String getId() {
		return id;
	}

	public void updateScheduledTime(LocalDateTime scheduledTime) {
		this.scheduledTime = scheduledTime;
	}

	public void cancel() {
		this.status = Status.CANCELED;
	}

	@DynamoDbConvertedBy(LocalDateTimeConverter.class)
	public LocalDateTime getScheduledTime() {
		return scheduledTime;
	}

	@DynamoDbConvertedBy(EnumMapAttributeConverter.class)
	public Map<CustomType, String> getCustomData() {
		return customData;
	}
}
