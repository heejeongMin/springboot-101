package com.pancho.young.springboot.config.auth;

import com.pancho.young.springboot.config.auth.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpSession;

/*
HandlerMethodArgumentResolver 는 한가지 기능을 지원함 .
조건에 맞는 경우 메서드가 있다면 HandlerMethodArgumentResolver의 구현체가 지정한 값으로 해당 메서드의 파라미터로 넘길 수 있음.

HandlerMethodArgumentResolver는 항공 WebMvcConfigurer의 addArgumentResolvers()를 통해 추가하여 사용할 수 있다.
WebConfig 클래스 참조.
 */
@RequiredArgsConstructor
@Component
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {

    private final HttpSession httpSession;

    /*
        컨트롤러 메서드의 특정 파라미터를 지원하는지 판단
        구현을 파라미터에 @LoginUser 어노테이션이 붙어있고, 파라미터 클래스 타입이 SessionUser.class 인경우 true 반환
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean isLoginUserAnnotation = parameter.getParameterAnnotation(LoginUser.class) != null;
        boolean isUserClass = SessionUser.class.equals(parameter.getParameterType());
        return isLoginUserAnnotation && isUserClass;
    }

    /*
    파라미터에 전달할 객체를 생성함.
    구현을 세션에서 객체를 가져오는 것으로 함.
     */
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        return httpSession.getAttribute("user");
    }
}
