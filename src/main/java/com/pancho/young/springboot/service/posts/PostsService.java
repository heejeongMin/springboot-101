package com.pancho.young.springboot.service.posts;

import com.pancho.young.springboot.domain.posts.Posts;
import com.pancho.young.springboot.domain.posts.PostsRepository;
import com.pancho.young.springboot.web.dto.PostsListResponseDto;
import com.pancho.young.springboot.web.dto.PostsResponseDto;
import com.pancho.young.springboot.web.dto.PostsSaveRequestDto;
import com.pancho.young.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsService {

    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto){
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto){
        Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id="+ id));

        posts.update(requestDto.getTitle(), requestDto.getContent());
        /*
        Posts 엔티티에만 저장하고 업데이트하는 쿼리를 날리지 않음 ?
        이게 가능한 이유는 JPA의 영속성 컨텍스트 때문임. 영속성 컨텍스트란 엔티티를 영구 저장하는 환경.
        JPA의 핵심 내용은 엔티티가 영속성 컨텍스트에 포함되어 있냐 아니냐로 갈림.

        JPA의 엔티티 매니저가 활성화된 상태로 (Spring Data Jpa를 쓴다면 default)
        트랜젝션 안에서 데이터베이스에서 데이터를 가져오면 이 데이터는 영속성 컨텍스트가 유지됨.

        이 상태에서 해당 데이터의 값을 변경하면 트랜잭션이 끝나느 시점에 해당 테이블에 변경분 반영.
        즉, Entity 객체의 값만 변경하면 별도로 update쿼리를 날릴 필요가 없음. 이 개념을 dirty checking이라고 함.
        JPA에서 트랜잭션이 끝나느 시점에 변화가 있는 모든 엔티티 객체를 (DIRTY된) 검사하여(CHECKING하여) 데이터베이스에 자동 반영해줌
        변화의 기준은 최초조회상태를 스냅샷을 만들어 놓은것이다. JPA는 기본적으로 모든 필드를 업데이트하지만,
        변경된 부분만 업데이트 하고싶다면 @DynamicUpdate를 사용한다.
         */

        return id;
    }

    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다.id=" + id));

        return new PostsResponseDto(entity);
    }

    @Transactional(readOnly = true) //option으로 readonly true를 주면 트랜잭션 범위는 유지되면서 조회기능만 남겨두어 조회속도가 개선된다.
    public List<PostsListResponseDto> findAllDesc() {
        return postsRepository.findAllDesc().stream().map(PostsListResponseDto::new)
                                                     .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id){
        Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        postsRepository.delete(posts);
    }
}
