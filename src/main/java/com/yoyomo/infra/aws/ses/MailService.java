package com.yoyomo.infra.aws.ses;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.*;

import static com.yoyomo.infra.aws.ses.dto.request.MailTemplateRequest.Save;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailService {

    //UUID로 DB에 저장해서 템플릿 이름으로 사용하도록 수정
    private final String TEMPLATE_NAME = "VerifyMailTemplate";
    private final SesClient sesClient;

    //템플릿 저장 - 비밀번호 전용
    public void saveTemplate(Save dto) {

        Template template = Template.builder()
                .templateName(dto.templateName())
                .subjectPart(dto.subject())
                .textPart(dto.textPart())
                .htmlPart(dto.htmlPart())
                .build();

        CreateTemplateRequest saveRequest = CreateTemplateRequest.builder()
                .template(template)
                .build();

        sesClient.createTemplate(saveRequest);
    }

    public void updateTemplate(Save dto) {

        Template template = Template.builder()
                .templateName(dto.templateName())
                .subjectPart(dto.subject())
                .textPart(dto.textPart())
                .htmlPart(dto.htmlPart())
                .build();

        UpdateTemplateRequest updateRequest = UpdateTemplateRequest.builder()
                .template(template)
                .build();

        sesClient.updateTemplate(updateRequest);
    }

    public Save getTemplate(String templateName) {

        GetTemplateRequest getRequest = GetTemplateRequest.builder()
                .templateName(templateName)
                .build();

        GetTemplateResponse response = sesClient.getTemplate(getRequest);

        // 우선 htmlPart만 반환. 요구사항 증가시 다른 요소도 함께 반환
        return new Save(
                response.template().templateName(),
                response.template().subjectPart(),
                response.template().htmlPart(),
                response.template().textPart()
        );
    }

    // 템플릿을 사용하여 이메일 보내기 (sendTemplatedEmail 사용)
    public void sendVerifyCode(String email, String code) {
        String templateData = "{\"CODE\":\"" + code + "\"}";

        SendTemplatedEmailRequest emailRequest = SendTemplatedEmailRequest.builder()
                .destination(Destination.builder().toAddresses(email).build())
                .template(TEMPLATE_NAME)
                .templateData(templateData)
                // 차후에 크레용 도메인 메일로 변경
                .source("ewgt1234@naver.com") // SES에 검증된 이메일 주소
                .build();

        sesClient.sendTemplatedEmail(emailRequest);
        log.info("템플릿을 사용한 이메일 발송 성공: {}", email);
    }

    // 이메일 인증 코드 전송 메서드. 요구사항에 맞게 사용할 것
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
