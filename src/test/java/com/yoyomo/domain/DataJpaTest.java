package com.yoyomo.domain;

import com.yoyomo.domain.application.domain.repository.mongo.AnswerRepository;
import com.yoyomo.domain.fixture.CustomRepository;
import com.yoyomo.domain.form.domain.repository.FormRepository;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.mock.mockito.MockBean;

@MockBean(classes = {
        AnswerRepository.class,
        FormRepository.class,
})
@org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class DataJpaTest {

    @Autowired
    CustomRepository customRepository;

    @AfterEach
    void tearDown() {
        customRepository.clearAndReset();
    }
}
