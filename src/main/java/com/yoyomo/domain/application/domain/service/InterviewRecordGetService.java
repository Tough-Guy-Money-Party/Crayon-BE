package com.yoyomo.domain.application.domain.service;

import com.yoyomo.domain.application.domain.entity.InterviewRecord;
import com.yoyomo.domain.application.domain.repository.InterviewRecordRepository;
import com.yoyomo.domain.application.exception.InterviewNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InterviewRecordGetService {

    private final InterviewRecordRepository interviewRecordRepository;

    public List<InterviewRecord> findAll(UUID applicationId) {
        return interviewRecordRepository.findAllByApplicationIdOrderByCreatedAtDesc(applicationId);
    }

    public InterviewRecord find(long interviewRecordId) {
        return interviewRecordRepository.findById(interviewRecordId)
                .orElseThrow(InterviewNotFoundException::new);
    }
}
