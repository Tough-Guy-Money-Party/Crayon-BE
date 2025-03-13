package com.yoyomo.domain.application.domain.service;

import com.yoyomo.domain.application.domain.entity.InterviewRecord;
import com.yoyomo.domain.application.domain.repository.InterviewRecordRepository;
import com.yoyomo.domain.application.exception.AccessDeniedException;
import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.club.domain.repository.ClubMangerRepository;
import com.yoyomo.domain.club.domain.repository.ClubRepository;
import com.yoyomo.domain.fixture.CustomRepository;
import com.yoyomo.domain.fixture.TestFixture;
import com.yoyomo.domain.user.domain.entity.User;
import com.yoyomo.domain.user.domain.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class InterviewRecordUpdateServiceTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ClubRepository clubRepository;

    @Autowired
    ClubMangerRepository clubMangerRepository;

    @Autowired
    InterviewRecordRepository interviewRecordRepository;

    @Autowired
    InterviewRecordUpdateService interviewRecordUpdateService;

    @Autowired
    CustomRepository customRepository;

    User user;

    @BeforeEach
    void setUp() {
        user = userRepository.save(TestFixture.user());
        Club club = clubRepository.save(TestFixture.club());
        clubMangerRepository.save(TestFixture.clubManager(club, user));
    }

    @AfterEach
    void tearDown() {
        customRepository.clearAndReset();
    }

    @DisplayName("면접 기록 작성자라면 삭제에 성공한다.")
    @Test
    void delete() {
        // given
        InterviewRecord savedInterviewRecord = interviewRecordRepository.save(TestFixture.interviewRecord(user));

        // when & then
        assertThatCode(() -> interviewRecordUpdateService.delete(savedInterviewRecord, user))
                .doesNotThrowAnyException();
        assertThat(interviewRecordRepository.findById(savedInterviewRecord.getId())).isEmpty();
    }

    @DisplayName("면접 기록 작성자가 아니라면 삭제에 실패한다.")
    @Test
    void delete_fail() {
        // given
        InterviewRecord interviewRecord = TestFixture.interviewRecord(user);
        interviewRecordRepository.save(interviewRecord);

        User newUser = userRepository.save(TestFixture.user());

        // when & then
        assertThatThrownBy(() -> interviewRecordUpdateService.delete(interviewRecord, newUser))
                .isInstanceOf(AccessDeniedException.class);
    }

    @DisplayName("면접 기록 작성자가 아니라면 수정에 실패한다.")
    @Test
    void update_fail() {
        // given
        InterviewRecord interviewRecord = TestFixture.interviewRecord(user);
        interviewRecordRepository.save(interviewRecord);

        User newUser = userRepository.save(TestFixture.user());

        // when & then
        assertThatThrownBy(() -> interviewRecordUpdateService.update(interviewRecord, newUser, "newContent"))
                .isInstanceOf(AccessDeniedException.class);
    }
}
