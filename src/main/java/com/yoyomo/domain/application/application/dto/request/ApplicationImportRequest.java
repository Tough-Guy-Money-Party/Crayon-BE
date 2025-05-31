package com.yoyomo.domain.application.application.dto.request;

import com.yoyomo.domain.application.domain.model.Question;
import com.yoyomo.domain.application.domain.model.Replies;

import java.util.List;

public record ApplicationImportRequest(
        List<QuestionRequest> cols,
        List<RespondentRequest> rows
) {

    public List<Question> toQuestions() {
        return cols.stream()
                .map(col -> new Question(col.label(), col.type()))
                .toList();
    }

    public List<Replies> toAnswers() {
        return rows.stream()
                .map(RespondentRequest::toAnswers)
                .toList();
    }
}
