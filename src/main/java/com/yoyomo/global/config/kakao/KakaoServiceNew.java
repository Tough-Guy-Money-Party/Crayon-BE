package com.yoyomo.global.config.kakao;

import com.yoyomo.global.config.kakao.dto.KakaoTokenResponse;
import com.yoyomo.global.config.kakao.dto.KakaoUserInfoResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Slf4j
@Service
public class KakaoServiceNew {

    private final WebClient webClient = WebClient.create();

    @Value("${kakao.token_uri}")
    private String tokenUri;

    @Value("${kakao.redirect_uri}")
    private String redirectUri;

    @Value("${kakao.grant_type}")
    private String grantType;

    @Value("${kakao.client_id}")
    private String clientId;

    @Value("${kakao.user_info_uri}")
    private String userInfoUri;

    public KakaoTokenResponse getToken(String code) {
        String uri = tokenUri + "?grant_type=" + grantType + "&client_id="
                + clientId + "&redirect_uri=" + redirectUri + "&code=" + code;

        Flux<KakaoTokenResponse> response = webClient.post()
                .uri(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(KakaoTokenResponse.class);

        return response.blockFirst();
    }

    public KakaoUserInfoResponse getUserInfo(String token) {

        Flux<KakaoUserInfoResponse> response = webClient.get()
                .uri(userInfoUri)
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .bodyToFlux(KakaoUserInfoResponse.class);

        return response.blockFirst();
    }

}
