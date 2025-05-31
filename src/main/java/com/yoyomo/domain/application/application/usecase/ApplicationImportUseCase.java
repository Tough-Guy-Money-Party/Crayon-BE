package com.yoyomo.domain.application.application.usecase;

import com.yoyomo.domain.application.application.dto.request.ApplicationImportRequest;
import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.application.domain.model.ApplicantReply;
import com.yoyomo.domain.application.domain.model.Question;
import com.yoyomo.domain.application.domain.model.Replies;
import com.yoyomo.domain.application.domain.service.AnswerSaveService;
import com.yoyomo.domain.application.domain.service.ApplicationSaveService;
import com.yoyomo.domain.club.domain.service.ClubManagerAuthService;
import com.yoyomo.domain.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ApplicationImportUseCase {

    private final ApplicationSaveService applicationSaveService;
    private final AnswerSaveService answerSaveService;
    private final ClubManagerAuthService clubManagerAuthService;

    @Transactional
    public void importApplications(UUID recruitmentId, User user, ApplicationImportRequest request) {
        clubManagerAuthService.checkAuthorization(recruitmentId, user);

        List<Question> questions = request.toQuestions();
        List<Replies> replies = request.toReplies();

        List<ApplicantReply> applicantReplies = replies.stream()
                .map(reply -> ApplicantReply.of(questions, reply))
                .toList();

        List<Application> applications = applicationSaveService.saveAll(recruitmentId, applicantReplies);
        answerSaveService.save(applicantReplies, applications);
    }
}
