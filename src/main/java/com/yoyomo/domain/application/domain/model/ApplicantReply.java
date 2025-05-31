package com.yoyomo.domain.application.domain.model;

import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.item.domain.entity.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.partitioningBy;

@Getter
@AllArgsConstructor
public class ApplicantReply {

    private final Applicant applicant;
    private final List<QuestionReply> questionReplyPairs;

    /**
     * 질문 목록과 답변 목록으로부터 ApplicantReply 객체를 생성합니다.
     * 질문과 답변을 인덱스별로 매칭하고, 지원자 정보(이름, 전화번호, 이메일)와 
     * 일반 질문 답변으로 분리합니다.
     *
     * @param questions 질문 목록
     * @param replies   답변 목록
     * @return 생성된 ApplicantReply 객체
     */
    public static ApplicantReply of(List<Question> questions, Replies replies) {
        int length = getLength(questions, replies);

        Map<Boolean, List<QuestionReply>> partitioned = IntStream.range(0, length)
                .mapToObj(i -> toQuestionReply(questions, replies, i))
                .collect(partitioningBy(QuestionReply::isApplicantInfo));

        List<QuestionReply> applicantInfoPairs = partitioned.get(true);
        List<QuestionReply> questionReplyPairs = partitioned.get(false);

        return new ApplicantReply(Applicant.from(applicantInfoPairs), questionReplyPairs);
    }

    private static int getLength(List<Question> questions, Replies replies) {
        if (questions.size() != replies.size()) {
            return Math.min(questions.size(), replies.size());
        }
        return questions.size();
    }

    private static QuestionReply toQuestionReply(List<Question> questions, Replies replies, int i) {
        Question question = questions.get(i);
        Reply reply = replies.get(i);
        return QuestionReply.of(question, reply);
    }

    public Application toApplication(UUID recruitmentId) {
        return Application.builder()
                .userName(applicant.getName())
                .tel(applicant.getPhone())
                .email(applicant.getEmail())
                .recruitmentId(recruitmentId)
                .build(); // todo: 프로세스 추가해야하나
    }

    public List<Item> toAnswers() {
        return questionReplyPairs.stream()
                .map(QuestionReply::toAnswer)
                .toList();
    }
}
