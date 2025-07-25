package com.yoyomo.domain.landing.domain.constant;

public enum ReservedSubDomain {
	ONBOARDING("onboarding.crayon.land"),
	API("api.crayon.land"),
	WWW("www.crayon.land"),
	SES_FIRST("qo2e5wsppk4hceu6x7dbceq3cvvb4oit._domainkey.crayon.land"),
	SES_SECOND("opwgium664nr4rmkvxo2rq6lcpjkroqd._domainkey.crayon.land"),
	SES_THIRD("aorlqtane4wavsx7vki4r33x2bpjdhsv._domainkey.crayon.land");

	private final String description;

	ReservedSubDomain(String description) {
		this.description = description;
	}

	public static boolean contains(String subDomain) {
		for (ReservedSubDomain reserved : values()) {
			if (reserved.name().equalsIgnoreCase(subDomain)) {
				return true;
			}
		}
		return false;
	}

	public String getDescription() {
		return description;
	}

}
