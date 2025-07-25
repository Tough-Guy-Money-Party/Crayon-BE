package com.yoyomo.domain.recruitment.application.dto.request;

import java.time.LocalDateTime;

import com.yoyomo.domain.recruitment.application.annotation.PeriodCheck;
import com.yoyomo.domain.recruitment.application.annotation.TimeCheck;
import com.yoyomo.domain.recruitment.domain.entity.Process;
import com.yoyomo.domain.recruitment.domain.entity.enums.Type;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class ProcessRequest {

	public record Save(
		@NotEmpty String title,
		@NotNull Integer stage,
		@NotNull Type type,
		@Valid @PeriodCheck Period period
	) {
		public Process toProcess() {
			return Process.builder()
				.title(title)
				.stage(stage)
				.type(type)
				.startAt(period.evaluation.time().startAt)
				.endAt(period.evaluation.time().endAt)
				.announceStartAt(period.announcement.time().startAt)
				.announceEndAt(period.announcement.time().endAt)
				.build();
		}
	}

	public record Update(
		@NotEmpty String title,
		@NotNull Integer stage,
		@NotNull Type type,
		@Valid @PeriodCheck Period period
	) {
	}

	public record Period(
		@Valid Evaluation evaluation,
		@Valid Announcement announcement
	) {
	}

	public record Evaluation(
		@TimeCheck Time time
	) {
	}

	public record Announcement(
		@TimeCheck Time time
	) {
	}

	public record Time(
		LocalDateTime startAt,
		LocalDateTime endAt
	) {
	}
}
