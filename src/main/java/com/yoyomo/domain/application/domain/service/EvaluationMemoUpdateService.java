package com.yoyomo.domain.application.domain.service;


import com.yoyomo.domain.application.domain.repository.EvaluationMemoRepository;
import com.yoyomo.domain.application.exception.MemoDeleteException;
import com.yoyomo.domain.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EvaluationMemoUpdateService {

    private final EvaluationMemoRepository evaluationMemoRepository;

    public void delete(long memoId, User manager) {
        long executeCount = evaluationMemoRepository.deleteByIdAndManager(memoId, manager);
        if (executeCount != 1) {
            throw new MemoDeleteException();
        }
    }
}
