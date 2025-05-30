package com.yoyomo.domain;

import com.yoyomo.domain.application.application.usecase.ApplicationVerifyUseCase;
import com.yoyomo.domain.fixture.CustomRepository;
import com.yoyomo.global.config.jwt.JwtProvider;
import com.yoyomo.global.config.kakao.KakaoServiceNew;
import org.junit.jupiter.api.AfterEach;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.ActiveProfiles;

@MockBean(classes = {
        ApplicationVerifyUseCase.class,
        KakaoServiceNew.class,
        JwtProvider.class,
        RedissonClient.class
})
@ComponentScan(
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.REGEX,
                pattern = {
                        "com.yoyomo.domain.mail.*",
                        "com.yoyomo.domain.landing.*",
                        "com.yoyomo.domain.template.*"
                }
        )
)
@SpringBootTest
@ActiveProfiles("test")
public class ApplicationTest {

    @Autowired
    CustomRepository customRepository;

    @AfterEach
    void tearDown() {
        customRepository.clearAndReset();
    }
}
