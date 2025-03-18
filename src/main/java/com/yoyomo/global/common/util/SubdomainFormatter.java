package com.yoyomo.global.common.util;

public class SubdomainFormatter {
    private static final String DOMAIN_FORMAT = "%s.crayon.land";

    private SubdomainFormatter() {
    }

    public static String formatSubdomain(String prefix) {
        return String.format(DOMAIN_FORMAT, prefix);
    }

    public static String formatPrefix(String subdomain) {
        int dotIndex = subdomain.indexOf(".");
        return subdomain.substring(0, dotIndex);
    }
}
