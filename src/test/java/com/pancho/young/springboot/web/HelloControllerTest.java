package com.pancho.young.springboot.web;

import com.pancho.young.springboot.config.auth.SecurityConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/*
web 테스트용으로 선언시 @Controller, @ControllerAdvice 사용가능.
단, @Service, @Component, @Repository는 사용할 수 없다.
 WebSecurityConfigureAdapter, WebMvcConfigurer를 비롯한 @ControllerAdivce, @Controller를 스캔하지만, @Repository, @Service, @Componen는 스캔 대상 아님.
 따라서 SecurityConfig를 제외하고 스캔하게 하면 SecurityConfig를 읽은 뒤, CustomOAuth2UserService를 읽다가 에러가 나지 않게 한다.

 JPA metamodel must not be empty! 에러가 나는 이유
 Application클래스에 @EnableJpaAuditing 을 사용하기 위해서 최소 하나의 Entity 클래스가 필요하다.
 @EnableJpaAuditing이 @SpringBootApplication과 함께 있다보니 @WebMvcTest에서도 스캔하게 됨. 둘을 분리해야함.
 JpaConfig 파일을 따로 만든다.

 */
@RunWith(SpringRunner.class) //스프링부트테스트와 JUnit 사이의 연결자 역할
@WebMvcTest(controllers = HelloController.class,
            excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes= SecurityConfig.class)
                }
            )
public class HelloControllerTest {

    @Autowired
    private MockMvc mvc; //웹 API를 테스트할때 사용. (HTTP GET, POST 등 테스트가

    @Test
    @WithMockUser(roles="USER")
    public void returnHello() throws Exception {
        String hello = "hello";

        mvc.perform(get("/hello"))
                .andExpect((status().isOk()))
                .andExpect(content().string(hello));
    }

    @Test
    @WithMockUser(roles="USER")
    public void returnHelloDto() throws Exception {
        String name = "hello";
        int amount = 1000;

        mvc.perform(get("/hello/dto")
                    .param("name", name)
                    .param("amount", String.valueOf(amount)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name", is(name)))
            .andExpect(jsonPath("$.amount", is(amount)));

    }
}
