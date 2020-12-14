package com.pancho.young.springboot.domain.posts;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest //아무 설정 없이 SpringBootTest를 사용하면 H2 데이터베이스를 자동으로 실행해 줌.
public class PostsRepositoryTest {

    @Autowired
    PostsRepository postsRepository;

    @After //단위 테스트가 끝날때마다 수행되는 메서드를 지정.
            // 보통 배포 전 전체 테스트를 수행할 때 테스트간 데이터 침범을 막기 위해 사용한다.
            // 여러 테스트가 동시에 수행되면 테스트용 데이터베이스인 H2가 데이터에 그대로 남아 있어
            // 다음 테스트 시 실패할 수가 있기 때문이다.
    public void cleanup(){
        postsRepository.deleteAll();
    }

    @Test
    public void getPosts(){
        //given
        String title = "테스트 게시글";
        String content = "테스트 본문";

                
        postsRepository.save(Posts.builder().title(title)
                                            .content(content)
                                            .author("hj.min1031@gmail.com")
                                            .build()); //save()는 테이블에 insert/update 쿼리 실행한다. ID 값이 있으면 update, 없으면 insert 실행

        //when
        List<Posts> postsList = postsRepository.findAll();

        //then
        Posts posts = postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);

    }
}
