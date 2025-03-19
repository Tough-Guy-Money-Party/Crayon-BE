package com.yoyomo.domain.application.domain.service;

import com.yoyomo.domain.ApplicationTest;
import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.application.domain.entity.ProcessResult;
import com.yoyomo.domain.application.domain.entity.enums.Status;
import com.yoyomo.domain.application.domain.repository.ApplicationRepository;
import com.yoyomo.domain.application.domain.repository.ProcessResultRepository;
import com.yoyomo.domain.fixture.TestFixture;
import com.yoyomo.domain.recruitment.domain.entity.Process;
import com.yoyomo.domain.recruitment.domain.repository.ProcessRepository;
import com.yoyomo.domain.user.domain.entity.User;
import com.yoyomo.domain.user.domain.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.yoyomo.domain.fixture.TestFixture.application;
import static com.yoyomo.domain.fixture.TestFixture.process;
import static com.yoyomo.domain.fixture.TestFixture.user;
import static org.assertj.core.api.Assertions.assertThat;

class ProcessResultGetServiceTest extends ApplicationTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProcessRepository processRepository;

    @Autowired
    ApplicationRepository applicationRepository;

    @Autowired
    ProcessResultRepository processResultRepository;

    @Autowired
    ProcessResultGetService processResultGetService;

    @DisplayName("현재 Process까지의 결과를 조회한다.")
    @Test
    void find_with_current_process_result() {
        //given
        User user = userRepository.save(user());
        Process beforeProcess = processRepository.save(process());
        Process currentProcess = processRepository.save(process());
        Application application = applicationRepository.save(application(user, currentProcess));

        ProcessResult beforeResult = processResultRepository.save(TestFixture.processResult(application.getId(), beforeProcess.getId(), Status.DOCUMENT_PASS));
        ProcessResult currentResult = processResultRepository.save(TestFixture.processResult(application.getId(), currentProcess.getId(), Status.PENDING));

        // when
        List<ProcessResult> processResults = processResultGetService.find(application);

        // then
        assertThat(processResults).containsExactly(beforeResult, currentResult);
    }

    @DisplayName("현재 Process의 결과가 없으면 평가전(BEFORE_EVALUATION) 상태로 조회한다.")
    @Test
    void find_without_current_process_result() {
        //given
        User user = userRepository.save(user());
        Process beforeProcess = processRepository.save(process());
        Process currentProcess = processRepository.save(process());
        Application application = applicationRepository.save(application(user, currentProcess));

        ProcessResult beforeResult = processResultRepository.save(TestFixture.processResult(application.getId(), beforeProcess.getId(), Status.DOCUMENT_PASS));

        // when
        List<ProcessResult> processResults = processResultGetService.find(application);

        // then
        ProcessResult currentResult = ProcessResult.getBeforeEvaluationResult(application.getId(), currentProcess.getId());
        assertThat(processResults).containsExactly(beforeResult, currentResult);
    }
}
