package com.yoyomo.domain.mail.domain.entity.enums;

import java.util.Map;
import java.util.stream.Collectors;

import software.amazon.awssdk.enhanced.dynamodb.AttributeConverter;
import software.amazon.awssdk.enhanced.dynamodb.AttributeValueType;
import software.amazon.awssdk.enhanced.dynamodb.EnhancedType;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

public class EnumMapAttributeConverter implements AttributeConverter<Map<CustomType, String>> {

	@Override
	public AttributeValue transformFrom(final Map<CustomType, String> input) {
		Map<String, AttributeValue> attributeValueMap =
			input.entrySet().stream()
				.collect(Collectors.toMap(
					k -> k.getKey().toString(),
					v -> AttributeValue.builder().s(v.getValue()).build()
				));
		return AttributeValue.builder().m(attributeValueMap).build();
	}

	@Override
	public Map<CustomType, String> transformTo(final AttributeValue input) {
		return input.m().entrySet().stream()
			.collect(Collectors.toMap(
				k -> getEnumClassKeyByString(k.getKey()),
				v -> v.getValue().s()
			));
	}

	private CustomType getEnumClassKeyByString(final String key) {
		return CustomType.valueOf(key);
	}

	@Override
	public EnhancedType<Map<CustomType, String>> type() {
		return EnhancedType.mapOf(CustomType.class, String.class);
	}

	@Override
	public AttributeValueType attributeValueType() {
		return AttributeValueType.M;
	}
}
