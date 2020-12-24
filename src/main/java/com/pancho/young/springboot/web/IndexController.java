package com.pancho.young.springboot.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class IndexController {

    @GetMapping("/")
    public String index(){
        return "index"; //파일이름만 적으면 viewResolver에서 자동으로 파일경로와 (src/main/resources/templates) 파일 확장자를 붙여줌 (.mustache)
    }

    @GetMapping("/posts/save")
    public String postsSave(){
        return "posts-save";
    }
}
