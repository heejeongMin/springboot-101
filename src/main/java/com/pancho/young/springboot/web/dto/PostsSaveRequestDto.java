package com.pancho.young.springboot.web.dto;

import com.pancho.young.springboot.domain.posts.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostsSaveRequestDto {
    private String title;
    private String content;
    private String author;

    @Builder
    public PostsSaveRequestDto(String title, String content, String author){
        this.title = title;
        this.content = content;
        this.author = author;
    }

    /*
    Entity 클래스와 거의 유사함에도 Dto 클래스를 추가로 생성하였음.
    이유는 Entity 클래스는 DB와 연결되는 핵심 클래스임으로 Request/Reponse로 사용하면 안됨.
    Rquest/Reponse용 Dto는 view를 위한 클래스라 자주 변경이 됨으로 View Layer와 DB Layer를 분리해야함
    실제로 Entity 클래스 만으로 표현하기 어려운경우 Controller에서 결과값으로 여러 테이블을 조인해서 줘야 하는 경우가 있음
     */
    public Posts toEntity(){
        return Posts.builder()
                    .title(title)
                    .content(content)
                    .author(author)
                    .build();
    }
}
