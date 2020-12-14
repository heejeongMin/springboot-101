package com.pancho.young.springboot.domain.posts;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


/*
롬복은 코드를 단순화 시켜주지만 필수 어노테이션은 아니고,
JPA는 필수 어노테이션임으로 클래스에 다 가깝게 둔다.
이렇게 하면 코틀린 등의 새 언어 전환으로 롬복이 더 이상 필요 없을 경우 쉽게 삭제할 수 있다.

Posts 클래스는 실제 DB 테이블과 매칭된 클래스이며, Entity 클래스라고 한다.
  ** Entity 클래스에는 Setter 메소드를 만들지 않는다. Setter 메서드가 있으면, 해당 클래스의 인스턴스 값들이 언제 어디서 변해야 하는지 코드상으로 명확히 구분할 수 없기 때문
  ** 기본적으로는 생성자를 통해 최종값을 채운 후 DB에 삽입한다.
  ** 값변경이 필요한 경우 그 목적과 의도를 나타낼수 있는(이벤트에 맞는) 메소드를 추가한다.
 */
@Getter //롬복 어노테이션
@NoArgsConstructor //롬복 어노테이션
@Entity //JPA 어노테이션. 테이블과 링크될 클래스를 나타냄.
        //기본값으로 클래스의 카멜케이스 이름을 언더스코어네이밍으로 테이블 이름을 매칭함
        // ex) SalesManager.java -> sales_manager table
public class Posts {

    @Id //PK를 나타냄냄
    @GeneratedValue(strategy = GenerationType.IDENTITY) // pk의 생성규칙을 나타냄
                    //1. 스프링 부트 2.0에서는 GenerationType.IDENTITY 옵션을 추가해야 auto_increment 된다. 그렇지 않으면 공용 TABLE SEQUENCE 따라기게 됨
                    //2. 스프링 부트 1.5는 DEFAULT인 AUTO를 사용하여도 정상 작동
    private Long id;

    @Column(length = 500, nullable = false) //테이블 컬럼을 나타내며 굳이 선언하지 않아도 해당 클래스의 필드는 모두 컬럼이 됨.
                                            // 기본값 외에 추가로 변경이 필요한 옵션이 있으면 사용하면 좋음.
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    @Builder// 해당 클래스의 빌더 패턴 클래스를 생성. 생성자 상단에 선언 시 생성자에 포함된 필드만 빌더에 포함.
            // 생성자나 빌더나 생성시점에 생성되는 것을 동일하나 빌더를 사용하면 어느 필드에 어떤 값을 채워야할 지 명확해진다.
            //EX. new Test(a, b)에서 a, b 위치를 변경해도 코드를 실행하기 전까지는 알 수 없다.
            //    Test.builder().a(a).b(b).build();
    public Posts(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }
}
