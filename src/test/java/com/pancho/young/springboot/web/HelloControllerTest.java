package com.pancho.young.springboot.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class) //스프링부트테스트와 JUnit 사이의 연결자 역할
@WebMvcTest(controllers = HelloController.class)// web 테스트용으로 선언시 @Controller, @ControllerAdvice 사용가능.
                                                // 단, @Service, @Component, @Repository는 사용할 수 없다.
public class HelloControllerTest {

    @Autowired
    private MockMvc mvc; //웹 API를 테스트할때 사용. (HTTP GET, POST 등 테스트가

    @Test
    public void returnHello() throws Exception {
        String hello = "hello";

        mvc.perform(get("/hello"))
                .andExpect((status().isOk()))
                .andExpect(content().string(hello));
    }

//    @Test
//    public void returnHelloDto() throws Exception {
//        String name = "hello";
//        int amount = 1000;
//
//        mvc.perform(get("/hello/dto")
//                    .param("name", name)
//                    .param("amount", String.valueOf(amount)))
//            .andExpect(status().isOk())
//            .andExpect(jsonPath("$.name", is(name)))
//            .andExpect(jsonPath("$.amount"), is(amount));
//
//    }
}
