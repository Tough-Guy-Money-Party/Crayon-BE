package com.yoyomo.domain.mail.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailVerifyService {

    private final String TEMPLATE_NAME = "VerifyMailTemplate";
    private final SesClient sesClient;

    public void sendVerifyCode(String email, String code) {
        String templateData = "{\"CODE\":\"" + code + "\"}";

        SendTemplatedEmailRequest emailRequest = SendTemplatedEmailRequest.builder()
                .destination(Destination.builder().toAddresses(email).build())
                .template(TEMPLATE_NAME)
                .templateData(templateData)
                .source("ewgt1234@naver.com") // SES에 검증된 이메일 주소
                .build();

        sesClient.sendTemplatedEmail(emailRequest);
        log.info("템플릿을 사용한 이메일 발송 성공: {}", email);
    }

    public void sendVerificationEmail(String email) {
        try {
            VerifyEmailIdentityRequest verifyRequest = VerifyEmailIdentityRequest.builder()
                    .emailAddress(email)
                    .build();

            VerifyEmailIdentityResponse response = sesClient.verifyEmailIdentity(verifyRequest);
            log.info("인증 이메일 전송 요청 성공: {}", response);
        } catch (SesException e) {
            log.error("인증 이메일 전송 요청 실패: {}", e.awsErrorDetails().errorMessage());
            throw new RuntimeException("인증 이메일 전송에 실패했습니다.", e);
        }
    }
}
