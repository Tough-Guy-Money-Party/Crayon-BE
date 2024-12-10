package com.yoyomo.domain.application.domain.service;

import com.yoyomo.domain.application.domain.entity.InterviewRecord;
import com.yoyomo.domain.application.domain.repository.InterviewRecordRepository;
import com.yoyomo.domain.application.exception.InterviewRecordAlreadyExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InterviewRecordSaveService {

    private final InterviewRecordRepository interviewRecordRepository;

    public InterviewRecord save(InterviewRecord interviewRecord) {
        if (interviewRecordRepository.existsByManagerAndApplicationId(interviewRecord.getManager(), interviewRecord.getApplicationId())) {
            throw new InterviewRecordAlreadyExistException();
        }

        return interviewRecordRepository.save(interviewRecord);
    }
}
