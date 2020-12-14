package com.pancho.young.springboot.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;

/*
SQL Mapper인 MyBatis에서는 DAO라고 불리는 DB Layer 접근자가 있고,
ORM 인 JPA에선 Repository라고 부르고 인터페이스로 생성하고,
JpaRepository<Entity 클래스, PK타입> 상속하여 CRUD 메서도르르 자동 생성한다.

*** Entity 클래스는 Repository 와 함께 위치해야한다. Entity 클래스는 Repository 없이 제대로 역할을 할 수 없음
*** 나중에 도메인별로 프로젝트를 분리해야한다면 Entity 클래스와 기본 Repository는 함께 움직여야 하므로 도메인 패키지에서 함께 관리한다.

 */
public interface PostsRepository extends JpaRepository<Posts, Long> {


}
