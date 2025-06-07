package com.yoyomo.domain.application.application.dto.request;

import com.yoyomo.domain.application.domain.vo.ApplicationReply;
import com.yoyomo.domain.application.domain.vo.Question;
import com.yoyomo.domain.application.domain.vo.QuestionReply;
import com.yoyomo.domain.application.domain.vo.Replies;
import com.yoyomo.domain.application.exception.QuestionReplySizeMismatchException;

import java.util.List;
import java.util.stream.IntStream;

public record ApplicationImportRequest(
        List<QuestionRequest> cols,
        List<RespondentRequest> rows
) {

    public List<ApplicationReply> toApplicationReplies() {
        List<Question> questions = toQuestions();
        List<Replies> replies = toReplies();

        return replies.stream()
                .map(reply -> toQuestionReplyList(questions, reply))
                .map(ApplicationReply::of)
                .toList();
    }

    private List<Question> toQuestions() {
        return cols.stream()
                .map(col -> new Question(col.label(), col.type()))
                .toList();
    }

    private List<Replies> toReplies() {
        return rows.stream()
                .map(RespondentRequest::toReplies)
                .toList();
    }

    private List<QuestionReply> toQuestionReplyList(List<Question> questions, Replies replies) {
        if (questions.size() != replies.size()) {
            throw new QuestionReplySizeMismatchException();
        }

        return IntStream.range(0, questions.size())
                .mapToObj(i -> QuestionReply.of(questions.get(i), replies.get(i)))
                .toList();
    }
}
