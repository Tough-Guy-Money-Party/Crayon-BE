package com.yoyomo.domain.application.domain.service;

import static com.yoyomo.domain.fixture.TestFixture.*;
import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.yoyomo.domain.ApplicationTest;
import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.application.domain.repository.ApplicationRepository;
import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.club.domain.repository.ClubRepository;
import com.yoyomo.domain.recruitment.domain.entity.Process;
import com.yoyomo.domain.recruitment.domain.repository.ProcessRepository;
import com.yoyomo.domain.recruitment.domain.repository.RecruitmentRepository;
import com.yoyomo.domain.user.domain.entity.User;
import com.yoyomo.domain.user.domain.repository.UserRepository;

import jakarta.persistence.EntityManager;

class ApplicationSaveServiceTest extends ApplicationTest {

	@Autowired
	private ClubRepository clubRepository;

	@Autowired
	private RecruitmentRepository recruitmentRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ApplicationSaveService applicationSaveService;

	@Autowired
	private ProcessRepository processRepository;

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private ApplicationRepository applicationRepository;

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

	@DisplayName("동시에 여러 개의 지원서가 들어올 경우 하나의 지원서만 저장된다.")
	@Test
	void test() throws InterruptedException {
		//given
		Club club = clubRepository.save(club());
		UUID recruitmentId = recruitmentRepository.save(recruitment(club)).getId();
		User user = userRepository.save(user());
		Process process = processRepository.save(process());

		//when
		int threadCount = 3;
		ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
		CountDownLatch latch = new CountDownLatch(threadCount);

		AtomicInteger successCount = new AtomicInteger(0);
		AtomicInteger exceptionCount = new AtomicInteger(0);
		List<Exception> exceptions = Collections.synchronizedList(new ArrayList<>());

		for (int i = 0; i < threadCount; i++) {
			executorService.submit(() -> {
				try {
					Application application = application(user, process, recruitmentId);

					applicationRepository.save(application);
					successCount.incrementAndGet();

				} catch (Exception e) {
					exceptions.add(e);
					exceptionCount.incrementAndGet();
				} finally {
					latch.countDown();
				}
			});
		}

		latch.await();
		executorService.shutdown();

		// then
		List<Application> savedApplications = applicationRepository.findAllByUserAndDeletedAtIsNull(user);
		assertThat(savedApplications).hasSize(1);
	}

}
