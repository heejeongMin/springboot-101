package com.pancho.young.springboot.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass// JPA Entity 클래스들이 BaseTimeEntity를 상속할 경우 필드들(createdDate, modifedDate)도 컬럼으로 인식하도록 한다. 0
@EntityListeners(AuditingEntityListener.class) // Auditing 기능을 포함시킴
public abstract class BaseTimeEntity { //모든 Entity의 상위 클래스가 되어 Entity들의 created Date, modifiedDate을 자동으로 관리하는 역할

    @CreatedDate //Entity가 생성되어 저장될 때 시간이 자동 저장
    private LocalDateTime createDate;

    @LastModifiedDate //조회한 Entity의 값을 변경할 때 시간이 자동 저장됨
    private LocalDateTime modifiedDate;
}
