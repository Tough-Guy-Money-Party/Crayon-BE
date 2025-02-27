package com.yoyomo.domain.application.domain.service;

import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.club.domain.repository.ClubRepository;
import com.yoyomo.domain.recruitment.domain.repository.RecruitmentRepository;
import com.yoyomo.domain.user.domain.entity.User;
import com.yoyomo.domain.user.domain.repository.UserRepository;
import com.yoyomo.fixture.CustomRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.yoyomo.fixture.TestFixture.application;
import static com.yoyomo.fixture.TestFixture.club;
import static com.yoyomo.fixture.TestFixture.recruitment;
import static com.yoyomo.fixture.TestFixture.user;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ApplicationSaveServiceTest {

    @Autowired
    private ClubRepository clubRepository;

    @Autowired
    private RecruitmentRepository recruitmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ApplicationSaveService applicationSaveService;

    @Autowired
    private CustomRepository customRepository;

    @Autowired
    private EntityManager entityManager;

    @AfterEach
    void tearDown() {
        customRepository.clearAndReset();
    }

    @DisplayName("동시에 지원하는 경우 지원자 수가 정상 반영된다.")
    @Test
    void save() throws InterruptedException {
        // given
        Club club = clubRepository.save(club());
        UUID recruitmentId = recruitmentRepository.save(recruitment(club)).getId();

        List<User> users = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            users.add(userRepository.save(user()));
        }

        // when
        int threadCount = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            int finalI = i;
            executorService.submit(() -> {
                try {
                    applicationSaveService.save(recruitmentId, application(users.get(finalI)));
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();
        executorService.shutdown();

        // then
        entityManager.clear();
        assertThat(recruitmentRepository.findById(recruitmentId))
                .isPresent()
                .hasValueSatisfying(result ->
                        assertThat(result.getTotalApplicantsCount()).isEqualTo(threadCount)
                );
    }
}
