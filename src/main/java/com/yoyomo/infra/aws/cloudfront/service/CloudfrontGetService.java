package com.yoyomo.infra.aws.cloudfront.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.services.cloudfront.CloudFrontClient;
import software.amazon.awssdk.services.cloudfront.model.DistributionSummary;
import software.amazon.awssdk.services.cloudfront.model.ListDistributionsRequest;
import software.amazon.awssdk.services.cloudfront.model.ListDistributionsResponse;

@Service
@RequiredArgsConstructor
public class CloudfrontGetService {
	private final CloudFrontClient cloudFrontClient;

	public List<DistributionSummary> getAllDistributions() {
		String nextMarker = null;
		List<DistributionSummary> allDistributions = new ArrayList<>();

		do {
			ListDistributionsResponse listDistributionsResponse = cloudFrontClient.listDistributions(
				ListDistributionsRequest.builder().marker(nextMarker).build()
			);

			allDistributions.addAll(listDistributionsResponse.distributionList().items());
			nextMarker = listDistributionsResponse.distributionList().nextMarker();
		} while (nextMarker != null);

		return allDistributions;
	}

}
