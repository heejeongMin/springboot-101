package com.pancho.young.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
//https://github.com/jojoldu/freelec-springboot2-webservice
@EnableJpaAuditing //JPA Auditing 활성화
@SpringBootApplication //스프링부트 자동설정, 스프링 Bean읽기/생성 모두 자동, 해당어노테이션이 있는 위치부터 설정을 읽음
public class Applicaiton {
    public static void main(String[] args){
        SpringApplication.run(Applicaiton.class, args);//내장 was 실행
    }
}
