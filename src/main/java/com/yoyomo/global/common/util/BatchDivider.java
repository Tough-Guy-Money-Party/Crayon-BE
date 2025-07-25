package com.yoyomo.global.common.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class BatchDivider {

	public <T> List<List<T>> divide(List<T> items, int batchSize) {
		List<List<T>> batches = new ArrayList<>();
		for (int i = 0; i < items.size(); i += batchSize) {
			batches.add(items.subList(i, Math.min(i + batchSize, items.size())));
		}
		return batches;
	}
}
