package com.yoyomo.global.common.resolver;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.yoyomo.domain.user.domain.entity.User;
import com.yoyomo.domain.user.domain.service.UserGetService;
import com.yoyomo.global.common.annotation.CurrentUser;
import com.yoyomo.global.config.jwt.JwtProvider;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class CurrentUserArgumentResolver implements HandlerMethodArgumentResolver {

	private final JwtProvider jwtProvider;
	private final UserGetService userGetService;

	@Override
	public boolean supportsParameter(MethodParameter parameter) {   // parameter가 해당 resolver를 지원하는 여부 확인
		boolean hasAnnotation = parameter.hasParameterAnnotation(CurrentUser.class);    // @CurrentUser이 존재하는가?
		boolean parameterType = User.class.isAssignableFrom(
			parameter.getParameterType());  // 파라미터 타입이 Long을 상속하거나 구현하였는가?
		return hasAnnotation && parameterType;  // 둘 다 충족할 시 true
	}

	@Override
	public User resolveArgument(MethodParameter parameter,
		ModelAndViewContainer mavContainer,
		NativeWebRequest webRequest,
		WebDataBinderFactory binderFactory) {
		HttpServletRequest httpServletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
		String accessToken = jwtProvider.extractAccessToken(httpServletRequest);
		Long id = jwtProvider.extractId(accessToken);
		return userGetService.find(id);
	}
}
