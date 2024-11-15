package com.yoyomo.domain.mail.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.scheduler.SchedulerClient;
import software.amazon.awssdk.services.scheduler.model.*;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.yoyomo.domain.mail.application.dto.MailRequest.Reserve;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailReserveService {

    private final SchedulerClient schedulerClient;

    @Value("${mail.lambda.arn}")
    private String arn;
    @Value("${mail.scheduler.arn}")
    private String roleArn;

    public void create(Reserve dto){
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
        CreateScheduleResponse response = schedulerClient.createSchedule(request);
        log.info("[MailReserveService]| 예약 메일 스케줄 생성 성공 {}", response.toString());
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
