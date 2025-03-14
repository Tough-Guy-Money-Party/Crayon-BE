package com.yoyomo.domain.recruitment.domain.service;

import com.yoyomo.domain.ApplicationTest;
import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.club.domain.entity.ClubManager;
import com.yoyomo.domain.club.domain.repository.ClubMangerRepository;
import com.yoyomo.domain.club.domain.repository.ClubRepository;
import com.yoyomo.domain.fixture.TestFixture;
import com.yoyomo.domain.form.domain.entity.Form;
import com.yoyomo.domain.form.domain.repository.FormRepository;
import com.yoyomo.domain.recruitment.domain.entity.Recruitment;
import com.yoyomo.domain.recruitment.domain.repository.RecruitmentRepository;
import com.yoyomo.domain.recruitment.exception.ModifiedRecruitmentException;
import com.yoyomo.domain.user.domain.entity.User;
import com.yoyomo.domain.user.domain.repository.UserRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static com.yoyomo.domain.fixture.TestFixture.club;
import static com.yoyomo.domain.fixture.TestFixture.recruitment;
import static com.yoyomo.domain.fixture.TestFixture.user;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RecruitmentUpdateServiceTest extends ApplicationTest {

    @Autowired
    ClubRepository clubRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ClubMangerRepository clubMangerRepository;

    @Autowired
    RecruitmentRepository recruitmentRepository;

    @Autowired
    FormRepository formRepository;

    @Autowired
    EntityManager entityManager;

    @Autowired
    RecruitmentUpdateService recruitmentUpdateService;

    @DisplayName("수정 중 활성화를 시도하면 활성화가 적용되지 않아야 한다.")
    @Test
    void updateConcurrentlyActivationFails() throws InterruptedException {
        // given: 기본 데이터 생성
        Club club = clubRepository.save(club());
        User user = userRepository.save(user());
        clubMangerRepository.save(ClubManager.asOwner(club, user));
        Recruitment recruitment = recruitmentRepository.save(recruitment(club));
        Form form = formRepository.save(TestFixture.form(club.getId().toString()));

        // when
        int threadCount = 2;
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);
        CountDownLatch updateLatch = new CountDownLatch(1);

        entityManager.clear();
        Recruitment freshRecruitment = recruitmentRepository.findById(recruitment.getId()).orElseThrow();
        LocalDateTime now = LocalDateTime.now();

        executorService.submit(() -> {
            try {
                freshRecruitment.update("새로운 제목", "새로운 포지션", now, now.plusDays(3));
                recruitmentRepository.save(freshRecruitment);
                updateLatch.countDown();
            } finally {
                latch.countDown();
            }
        });

        Future<?> activateFuture = executorService.submit(() -> {
            try {
                updateLatch.await();
                recruitmentUpdateService.update(recruitment, form.getId());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                latch.countDown();
            }
        });

        latch.await();
        executorService.shutdown();

        // then
        assertThatThrownBy(activateFuture::get)
                .isInstanceOf(ExecutionException.class)
                .hasCauseInstanceOf(ModifiedRecruitmentException.class);

        entityManager.clear();
        assertThat(recruitmentRepository.findById(recruitment.getId()))
                .isPresent()
                .hasValueSatisfying(r -> {
                    assertThat(r.getTitle()).isEqualTo("새로운 제목");
                    assertThat(r.isActive()).isFalse();
                });
    }
}
