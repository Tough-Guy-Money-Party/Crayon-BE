package com.yoyomo.domain;

import com.yoyomo.domain.application.application.usecase.ApplicationVerifyUseCase;
import com.yoyomo.global.config.jwt.JwtProvider;
import com.yoyomo.global.config.kakao.KakaoServiceNew;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@MockBean(classes = {
        ApplicationVerifyUseCase.class,
        KakaoServiceNew.class,
        JwtProvider.class,
})
@SpringBootApplication
@ComponentScan(
        basePackages = "com.yoyomo.domain",
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.REGEX,
                pattern = {
                        "com.yoyomo.domain.mail.*",
                        "com.yoyomo.domain.landing.*",
                        "com.yoyomo.domain.template.*"
                }
        )
)
public class DomainApplicationTest {
}
