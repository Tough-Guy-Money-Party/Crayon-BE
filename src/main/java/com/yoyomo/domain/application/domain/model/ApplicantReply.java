package com.yoyomo.domain.application.domain.model;

import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.item.domain.entity.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class ApplicantReply {

    private final Applicant applicant;
    private final List<QuestionReply> questionReplyPairs;

    public static ApplicantReply toApplicantReply(List<Question> questions, Replies replies) {
        List<Reply> replyList = replies.toList();

        Map<ApplicantInfo, Reply> applicant = new HashMap<>();
        List<QuestionReply> pairs = new ArrayList<>();
        for (int i = 0; i < getLength(questions, replyList); i++) {
            Question question = questions.get(i);
            Reply reply = replyList.get(i);

            if (question.isApplicantInfo()) {
                ApplicantInfo info = ApplicantInfo.find(question);
                applicant.put(info, reply);
                continue;
            }

            QuestionReply questionReply = QuestionReply.of(question, reply);
            pairs.add(questionReply);
        }

        return new ApplicantReply(new Applicant(applicant), pairs);
    }

    private static int getLength(List<Question> questions, List<Reply> replies) {
        if (questions.size() != replies.size()) {
            return Math.min(questions.size(), replies.size());
        }
        return questions.size();
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
