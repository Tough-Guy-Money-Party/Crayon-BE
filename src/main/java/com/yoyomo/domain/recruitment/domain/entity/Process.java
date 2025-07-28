package com.yoyomo.domain.recruitment.domain.entity;

import static com.yoyomo.domain.recruitment.domain.entity.enums.ProcessStep.*;
import static com.yoyomo.domain.recruitment.presentation.constant.ResponseMessage.*;

import java.time.LocalDateTime;

import com.yoyomo.domain.mail.exception.MailAlreadySentException;
import com.yoyomo.domain.mail.exception.MailNotScheduledException;
import com.yoyomo.domain.recruitment.domain.entity.enums.ProcessStep;
import com.yoyomo.domain.recruitment.domain.entity.enums.Type;
import com.yoyomo.domain.recruitment.exception.ProcessStepUnModifiableException;
import com.yoyomo.global.common.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class Process extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "process_id")
	private Long id;

	private String title;

	private int stage;

	@Builder.Default
	@Column(nullable = false, name = "process_step")
	@Enumerated(EnumType.STRING)
	private ProcessStep processStep = EVALUATION;

	@Enumerated(EnumType.STRING)
	private Type type;

	private LocalDateTime startAt;

	private LocalDateTime endAt;

	private LocalDateTime announceStartAt;

	private LocalDateTime announceEndAt;

	private LocalDateTime mailScheduledAt;

	@ManyToOne
	@JoinColumn(name = "recruitment_id")
	private Recruitment recruitment;

	public static Process replicate(Process process) {
		return Process.builder()
			.title(process.title)
			.stage(process.stage)
			.processStep(EVALUATION)
			.type(process.type)
			.startAt(process.startAt)
			.endAt(process.endAt)
			.announceStartAt(process.announceStartAt)
			.announceEndAt(process.announceEndAt)
			.build();
	}

	public Process addRecruitment(Recruitment recruitment) {
		return Process.builder()
			.id(id)
			.title(title)
			.stage(stage)
			.processStep(processStep)
			.type(type)
			.startAt(startAt)
			.endAt(endAt)
			.recruitment(recruitment)
			.announceStartAt(announceStartAt)
			.announceEndAt(announceEndAt)
			.build();
	}

	public void updateStep(ProcessStep step) {
		this.processStep = step;
	}

	public void reserve(LocalDateTime scheduledTime) {
		this.mailScheduledAt = scheduledTime;
	}

	public void cancelMail() {
		this.mailScheduledAt = null;
	}

	public void updateSchedule(LocalDateTime scheduledTime) {
		this.mailScheduledAt = scheduledTime;
	}

	public void checkMailScheduled() {
		if (this.mailScheduledAt == null) {
			throw new MailNotScheduledException();
		}

		if (isAfterMailSent()) {
			throw new MailAlreadySentException();
		}
	}

	public void checkMovable(Type currentProcess, ProcessStep step) {
		if (this.type != currentProcess) {
			throw new ProcessStepUnModifiableException(PROCESS_STEP_CANNOT_UPDATE);
		}

		if (step == EVALUATION && this.isAfterMailSent()) {
			throw new ProcessStepUnModifiableException(CANNOT_UPDATE_TO_EVALUATION_STEP);
		}
	}

	private boolean isAfterMailSent() {
		if (this.mailScheduledAt != null) {
			return LocalDateTime.now().isAfter(this.getMailScheduledAt());
		}
		return false;
	}

	public void updateDate(LocalDateTime startAt, LocalDateTime endAt) {
		this.startAt = startAt;
		this.endAt = endAt;
	}
}
