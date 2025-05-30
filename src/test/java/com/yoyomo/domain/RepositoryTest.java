package com.yoyomo.domain;

import com.yoyomo.domain.application.domain.repository.mongo.AnswerRepository;
import com.yoyomo.domain.fixture.CustomRepository;
import com.yoyomo.domain.form.domain.repository.FormRepository;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;

@MockBean(classes = {
        AnswerRepository.class,
        FormRepository.class,
        MongoTemplate.class
})
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Import(CustomRepository.class)
@ActiveProfiles("test")
public class RepositoryTest {

    @Autowired
    CustomRepository customRepository;

    @AfterEach
    void tearDown() {
        customRepository.clearAndReset();
    }
}
