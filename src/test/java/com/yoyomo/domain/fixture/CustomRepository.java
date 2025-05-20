package com.yoyomo.domain.fixture;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CustomRepository {

    @Autowired
    private EntityManager entityManager;

    @Transactional
    public void clearAndReset() {
        entityManager.createNativeQuery("DELETE FROM application").executeUpdate();
        entityManager.createNativeQuery("DELETE FROM interview_record").executeUpdate();
        entityManager.createNativeQuery("DELETE FROM recruitment").executeUpdate();
        entityManager.createNativeQuery("DELETE FROM club_manager").executeUpdate();
        entityManager.createNativeQuery("DELETE FROM club").executeUpdate();
        entityManager.createNativeQuery("DELETE FROM user").executeUpdate();

        entityManager.createNativeQuery("ALTER TABLE user ALTER COLUMN user_id RESTART WITH 1").executeUpdate();
    }
}
