package com.yoyomo.domain.application.domain.service;

import com.yoyomo.domain.application.domain.entity.InterviewRecord;
import com.yoyomo.domain.application.domain.repository.InterviewRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InterviewRecordSaveService {

    private final InterviewRecordRepository interviewRecordRepository;

    public InterviewRecord save(InterviewRecord interviewRecord) {
        return interviewRecordRepository.save(interviewRecord);
    }
}
