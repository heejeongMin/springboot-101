package com.pancho.young.springboot.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/*
SQL Mapper인 MyBatis에서는 DAO라고 불리는 DB Layer 접근자가 있고,
ORM 인 JPA에선 Repository라고 부르고 인터페이스로 생성하고,
JpaRepository<Entity 클래스, PK타입> 상속하여 CRUD 메서도르르 자동 생성한다.

*** Entity 클래스는 Repository 와 함께 위치해야한다. Entity 클래스는 Repository 없이 제대로 역할을 할 수 없음
*** 나중에 도메인별로 프로젝트를 분리해야한다면 Entity 클래스와 기본 Repository는 함께 움직여야 하므로 도메인 패키지에서 함께 관리한다.

 */
public interface PostsRepository extends JpaRepository<Posts, Long> {

    /*
        규모가 있는 프로젝트에서 데이터 조회는 FK 조인, 복잡한 조인 조건 등으로 Entity 클래스 만으로는 처리가 힘들어 조회용 프레임워크를 추가로 사용한다.
        대표적인 예로 querydsl, jooq, MyBatis등이 있고, 조회는 이 3가지 프레임워크 중 하나를 사용하고, 등록/수정/삭제는 SpringDataJpa를 통해 진행함.

        querydsl 같은경우, 타입안정성 보장, 국내 많은 회사에서 사용하고있어 레퍼런스도 많이 있다.

    */
    @Query("SELECT p FROM Posts p ORDER BY p.id DESC")
    List<Posts> findAllDesc();


}
