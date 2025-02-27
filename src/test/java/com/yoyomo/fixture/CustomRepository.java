package com.yoyomo.fixture;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
public class CustomRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void clearAndReset() {
        entityManager.createNativeQuery("DELETE FROM application").executeUpdate();
        entityManager.createNativeQuery("DELETE FROM recruitment").executeUpdate();
        entityManager.createNativeQuery("DELETE FROM club").executeUpdate();
        entityManager.createNativeQuery("DELETE FROM user").executeUpdate();

        entityManager.createNativeQuery("ALTER TABLE user ALTER COLUMN user_id RESTART WITH 1").executeUpdate();
    }
}
