package com.yoyomo.domain.mail.domain.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.yoyomo.domain.mail.application.dto.request.MailRequest;
import com.yoyomo.domain.mail.exception.CreateScheduleException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.services.scheduler.SchedulerClient;
import software.amazon.awssdk.services.scheduler.model.CreateScheduleRequest;
import software.amazon.awssdk.services.scheduler.model.CreateScheduleResponse;
import software.amazon.awssdk.services.scheduler.model.FlexibleTimeWindow;
import software.amazon.awssdk.services.scheduler.model.ScheduleState;
import software.amazon.awssdk.services.scheduler.model.SchedulerException;
import software.amazon.awssdk.services.scheduler.model.Target;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailReserveService {

	private final SchedulerClient schedulerClient;

	@Value("${mail.lambda.arn}")
	private String arn;
	@Value("${mail.scheduler.arn}")
	private String roleArn;

	public void create(MailRequest dto) {
		String cron = toCron(dto.scheduledTime());

		// 환경변수 처리
		Target target = Target.builder()
			.arn(arn) // Lambda 함수 ARN
			.roleArn(roleArn) // EventBridge Scheduler가 Lambda를 호출할 수 있도록 권한을 부여한 역할 ARN
			.build();

		// 유연한 처리 끄기
		FlexibleTimeWindow flexibleTimeWindow = FlexibleTimeWindow.builder()
			.mode("OFF")
			.build();

		CreateScheduleRequest request = CreateScheduleRequest.builder()
			.name(UUID.randomUUID().toString())
			.scheduleExpression(cron) // Cron 표현식 사용
			.scheduleExpressionTimezone("Asia/Seoul") // 타임존 설정
			.flexibleTimeWindow(flexibleTimeWindow)
			.target(target)
			.state(ScheduleState.ENABLED)
			.build();

		// Schedule 생성
		try {
			CreateScheduleResponse response = schedulerClient.createSchedule(request);
			log.info("[MailReserveService]| 예약 메일 스케줄 생성 성공 {}", response.toString());
		} catch (SchedulerException e) {
			throw new CreateScheduleException(e.getMessage());
		}
	}

	private String toCron(LocalDateTime scheduledTime) {
		return String.format("cron(%d %d %d %d ? %d)",
			scheduledTime.getMinute(),
			scheduledTime.getHour(),
			scheduledTime.getDayOfMonth(),
			scheduledTime.getMonthValue(),
			scheduledTime.getYear());
	}

}
