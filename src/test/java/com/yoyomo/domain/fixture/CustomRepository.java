package com.yoyomo.domain.fixture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@Repository
public class CustomRepository {

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Transactional
	public void clearAndReset() {
		entityManager.createNativeQuery("DELETE FROM evaluation").executeUpdate();
		entityManager.createNativeQuery("DELETE FROM application").executeUpdate();
		entityManager.createNativeQuery("DELETE FROM interview_record").executeUpdate();
		entityManager.createNativeQuery("DELETE FROM process_result").executeUpdate();
		entityManager.createNativeQuery("DELETE FROM process").executeUpdate();
		entityManager.createNativeQuery("DELETE FROM recruitment").executeUpdate();
		entityManager.createNativeQuery("DELETE FROM club_manager").executeUpdate();
		entityManager.createNativeQuery("DELETE FROM club").executeUpdate();
		entityManager.createNativeQuery("DELETE FROM user").executeUpdate();

		mongoTemplate.dropCollection("answers");
		mongoTemplate.dropCollection("forms");

		entityManager.createNativeQuery("ALTER TABLE user ALTER COLUMN user_id RESTART WITH 1").executeUpdate();
	}
}
