package com.yoyomo.domain.application.domain.service;

import com.yoyomo.domain.application.domain.entity.InterviewRecord;
import com.yoyomo.domain.application.domain.repository.InterviewRecordRepository;
import com.yoyomo.domain.application.exception.AccessDeniedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InterviewRecordUpdateService {

    private final InterviewRecordRepository interviewRecordRepository;

    public void delete(InterviewRecord interviewRecord, long userId) {
        if (!interviewRecord.isMine(userId)) {
            throw new AccessDeniedException();
        }
        interviewRecordRepository.delete(interviewRecord);
    }

    public void update(InterviewRecord interviewRecord, long managerId, String content) {
        if (!interviewRecord.isMine(managerId)) {
            throw new AccessDeniedException();
        }

        interviewRecord.update(content);
    }
}
