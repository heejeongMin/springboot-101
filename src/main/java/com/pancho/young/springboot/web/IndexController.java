package com.pancho.young.springboot.web;

import com.pancho.young.springboot.config.auth.LoginUser;
import com.pancho.young.springboot.config.auth.dto.SessionUser;
import com.pancho.young.springboot.service.posts.PostsService;
import com.pancho.young.springboot.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

/*
    세션이 내장 톰캣의 메모리에 저장되기 때문에 현재는 app재시작 시 로그인 풀림
    현업에서 사용하는 방법
    1. 톰캣 세션
        -> default로 2대이상의 was 구동 시 톰캣 간에 세션 공유를 위한 추가 설정필요
    2. 데이터베이스
        -> was 간 공용 세션을 사용할 수 있는 가장 쉬운 방법
        -> 하지만 요청마다 DB IO가 발생 (성능 이슈 고려), 보통 로그인 요청이 많이 없는 백오피트, 사내에서 사용
    3. 메모리DB (DB. Redis, Memcached)
        -> B2C에서 많이 사용. 실제 서비스로 사용하려면 Embedded Redis가 아닌 외부 메모리서버 필요

 */
@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;
    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user){
        model.addAttribute("posts", postsService.findAllDesc());

        if(user != null) {
            model.addAttribute("userName", user.getName());
        }

        return "index"; //파일이름만 적으면 viewResolver에서 자동으로 파일경로와 (src/main/resources/templates) 파일 확장자를 붙여줌 (.mustache)
    }

    @GetMapping("/posts/save")
    public String postsSave(){
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model){
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);

        return "posts-update";
    }
}
