package com.pancho.young.springboot.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/*
스프링 시큐리티에서는 권한 코드에 항상 ROLE_이 앞에 있어야한다.
그래서 코드별 키 값이 ROLE_ 로 시작함.
 */
@Getter
@RequiredArgsConstructor
public class Role {

//    GUEST("ROLE_GUEST", "손님"),
//    USER("ROLE_USER", "일반 사용자");
//
//    private final String key;
//    private final String title;
}
