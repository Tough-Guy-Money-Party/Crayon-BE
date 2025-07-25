package com.yoyomo.global.common.util;

import com.yoyomo.domain.landing.exception.InvalidFormatException;

public class SubdomainFormatter {
	private static final String DOMAIN_FORMAT = "%s.crayon.land";

	private SubdomainFormatter() {
	}

	public static String formatSubdomain(String prefix) {

		if (!prefix.matches("[a-z0-9-]+")) {
			throw new InvalidFormatException();
		}

		String lowerPrefix = prefix.toLowerCase();
		return String.format(DOMAIN_FORMAT, lowerPrefix);
	}

	public static String formatPrefix(String subdomain) {
		int dotIndex = subdomain.indexOf(".");
		return subdomain.substring(0, dotIndex);
	}
}
