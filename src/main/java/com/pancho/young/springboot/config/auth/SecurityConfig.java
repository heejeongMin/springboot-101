package com.pancho.young.springboot.config.auth;

import com.pancho.young.springboot.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity //Spring Security 설정 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable().headers().frameOptions().disable() //h2-console 화면을 사용하기 위함이라고 함.
                .and()
                .authorizeRequests() //URL별 권한 관리를 설정하는 옵션 시작점으로 이 다음부터 antMatchers 옵션 사용 가능
                //antMatchers는 권한 관리 대상을 지정하는옵션으로, URL, HTTP메서드별 관리 가능. permitAll을 주면 전체 열람 권한
                .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**").permitAll()
                //아래 url 사용하는 api는 user 권한을 가진사람만 가능
                .antMatchers("/api/v1/**").hasRole(Role.USER.name())
                .anyRequest() //antMatchers에서 지정한 것들 제외하고 나머지 요청
                .authenticated() //나머지 모든 url들은 모두 인증된 사용자만 허용하게 함.
                .and()
                .logout().logoutSuccessUrl("/")
                .and()
                .oauth2Login() //OAuth2 로그인 기능에 대한 여러 설정의 진입점
                .userInfoEndpoint() //OAuth2 로그인 성공 이후 사용자 정보 가져올때 설정 담당
                .userService(customOAuth2UserService); //소셜 로그인 성공시 후속조치를 진행할 UserService 인터페이스 구현체 등록

    }
}
