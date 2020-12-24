package com.pancho.young.springboot.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class IndexController {

    @GetMapping
    public String index(){
        return "index"; //mustach 파일은 컨트롤러에서 파일명으로 반환하면 앞 경로와 뒤 확장자는 view resolver가 자동으로 지정
    }

    @GetMapping("/posts/save")
    public String postsSave(){
        return "posts-save";
    }

}
