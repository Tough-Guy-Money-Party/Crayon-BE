package com.yoyomo.global.common.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.yoyomo.domain.application.exception.InvalidDateFormat;

public class DateFormatter {

	private static final String MAIL_DATE_FORMAT = "M월 d일(E) HH:mm";
	private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm";
	private static final List<FormatterEntry> ENTRIES = List.of(
		new FormatterEntry(
			Pattern.compile("^Date\\((\\d+),(\\d+),(\\d+),(\\d+),(\\d+),(\\d+)\\)$"),
			"%04d-%02d-%02d %02d:%02d"
		),
		new FormatterEntry(
			Pattern.compile("^Date\\((\\d+),(\\d+),(\\d+)\\)$"),
			"%04d-%02d-%02d"
		)
	);

	private DateFormatter() {
	}

	public static String formatMailDate(String date) {
		LocalDateTime dateTime = LocalDateTime.parse(date);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(MAIL_DATE_FORMAT, Locale.KOREAN);
		return dateTime.format(formatter);
	}

	public static String format(String dateTime) {
		if (dateTime == null || dateTime.isBlank()) {
			return null;
		}

		String trimmed = dateTime.trim();
		for (FormatterEntry entry : ENTRIES) {
			Matcher m = entry.pattern.matcher(trimmed);
			if (m.matches()) {
				return format(entry, m);
			}
		}
		throw new InvalidDateFormat();
	}

	public static LocalDateTime parse(String dateTime) {
		return LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));
	}

	private static String format(FormatterEntry entry, Matcher m) {
		int groupCount = m.groupCount();
		Object[] args = new Object[groupCount];
		for (int i = 1; i <= groupCount; i++) {
			args[i - 1] = Integer.parseInt(m.group(i));
		}
		return String.format(entry.format, args);
	}

	private static class FormatterEntry {
		private final Pattern pattern;
		private final String format;

		FormatterEntry(Pattern pattern, String format) {
			this.pattern = pattern;
			this.format = format;
		}
	}
}
