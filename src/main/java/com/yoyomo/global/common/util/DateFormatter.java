package com.yoyomo.global.common.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateFormatter {

    private static final String MAIL_DATE_FORMAT = "M월 d일(E) HH:mm";

    private DateFormatter() {
    }

    public static String formatMailDate(String date) {
        LocalDateTime dateTime = LocalDateTime.parse(date);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(MAIL_DATE_FORMAT, Locale.KOREAN);
        return dateTime.format(formatter);
    }
}
