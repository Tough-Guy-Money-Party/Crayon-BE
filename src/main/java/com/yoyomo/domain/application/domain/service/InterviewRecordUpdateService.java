package com.yoyomo.domain.application.domain.service;

import com.yoyomo.domain.application.domain.entity.InterviewRecord;
import com.yoyomo.domain.application.domain.repository.InterviewRecordRepository;
import com.yoyomo.domain.application.exception.AccessDeniedException;
import com.yoyomo.domain.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InterviewRecordUpdateService {

    private final InterviewRecordRepository interviewRecordRepository;

    public void delete(InterviewRecord interviewRecord, User user) {
        if (!interviewRecord.isMine(user)) {
            throw new AccessDeniedException();
        }
        interviewRecordRepository.delete(interviewRecord);
    }

    public void update(InterviewRecord interviewRecord, User user, String content) {
        if (!interviewRecord.isMine(user)) {
            throw new AccessDeniedException();
        }

        interviewRecord.update(content);
    }
}
