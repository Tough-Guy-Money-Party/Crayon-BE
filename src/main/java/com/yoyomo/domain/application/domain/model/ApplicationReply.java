package com.yoyomo.domain.application.domain.model;

import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.item.domain.entity.Item;
import com.yoyomo.domain.recruitment.domain.entity.Process;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static java.util.stream.Collectors.groupingBy;

@Getter
@AllArgsConstructor
public class ApplicationReply {

    private final Applicant applicant;
    private final List<QuestionReply> formQuestionReplies;

    /**
     * 질문&답변 목록으로부터 ApplicationReply 객체를 생성합니다.
     * 지원자 정보(이름, 전화번호, 이메일)와 일반 질문 답변으로 분리합니다.
     *
     * @param questionReplies 질문답변 리스트
     * @return 생성된 ApplicationReply 객체
     */
    public static ApplicationReply of(List<QuestionReply> questionReplies) {
        Map<QuestionCategory, List<QuestionReply>> questionRepliesPartition = questionReplies.stream()
                .collect(groupingBy(QuestionReply::getCategory));

        List<QuestionReply> applicantQuestionReplies = questionRepliesPartition.get(QuestionCategory.APPLICANT_INFO);
        List<QuestionReply> formQuestionReplies = questionRepliesPartition.get(QuestionCategory.FORM);

        Applicant applicant = Applicant.from(applicantQuestionReplies);
        return new ApplicationReply(applicant, formQuestionReplies);
    }

    public Application toApplication(UUID recruitmentId, Process process) {
        return Application.builder()
                .userName(applicant.getName())
                .tel(applicant.getPhone())
                .email(applicant.getEmail())
                .recruitmentId(recruitmentId)
                .process(process)
                .build();
    }

    public List<Item> toAnswers() {
        return formQuestionReplies.stream()
                .map(QuestionReply::toAnswer)
                .toList();
    }
}
