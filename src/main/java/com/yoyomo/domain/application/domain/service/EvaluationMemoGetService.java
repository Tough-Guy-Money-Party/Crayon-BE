package com.yoyomo.domain.application.domain.service;

import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.application.domain.entity.EvaluationMemo;
import com.yoyomo.domain.application.domain.repository.EvaluationMemoRepository;
import com.yoyomo.domain.application.exception.MemoUpdateException;
import com.yoyomo.domain.recruitment.domain.entity.Process;
import com.yoyomo.domain.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EvaluationMemoGetService {

    private final EvaluationMemoRepository evaluationMemoRepository;

    public List<EvaluationMemo> findAllInStage(Application application) {
        Process process = application.getProcess();

        return evaluationMemoRepository.findAllByProcessIdAndApplication(process.getId(), application);
    }

    public EvaluationMemo findByIdAndManagerAndManager(long memoId, User manager) {
        return evaluationMemoRepository.findByIdAndManager(memoId, manager)
                .orElseThrow(MemoUpdateException::new);
    }
}
