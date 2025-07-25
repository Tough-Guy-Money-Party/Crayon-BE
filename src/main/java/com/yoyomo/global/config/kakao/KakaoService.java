package com.yoyomo.global.config.kakao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.util.UriComponentsBuilder;

import com.yoyomo.global.config.kakao.dto.KakaoTokenResponse;
import com.yoyomo.global.config.kakao.dto.KakaoUserInfoResponse;
import com.yoyomo.global.config.kakao.exception.KakaoTokenException;
import com.yoyomo.global.config.kakao.exception.KakaoUserInfoException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KakaoService {

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
		String uri = UriComponentsBuilder.fromHttpUrl(tokenUri)
			.queryParam("grant_type", grantType)
			.queryParam("client_id", clientId)
			.queryParam("redirect_uri", redirectUri)
			.queryParam("code", code)
			.toUriString();

		return webClient.post()
			.uri(uri)
			.contentType(MediaType.APPLICATION_JSON)
			.retrieve()
			.bodyToFlux(KakaoTokenResponse.class)
			.onErrorMap(WebClientResponseException.class,
				e -> new KakaoTokenException()).blockFirst();
	}

	public KakaoUserInfoResponse getUserInfo(String token) {
		return webClient.get()
			.uri(userInfoUri)
			.header("Authorization", "Bearer " + token)
			.retrieve()
			.bodyToFlux(KakaoUserInfoResponse.class)
			.onErrorMap(WebClientResponseException.class,
				e -> new KakaoUserInfoException()).blockFirst();
	}

}
