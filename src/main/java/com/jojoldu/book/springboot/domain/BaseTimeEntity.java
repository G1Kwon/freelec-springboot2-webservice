package com.jojoldu.book.springboot.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
//JPA Entity 클래스들이 BastTimeEntity을 상속할 경우 필드들도 칼럼으로 인식하도록 한다.
@MappedSuperclass
//BaseTimeEntity클래스에 Auditing 기능을 포함시킨다.
@EntityListeners(AuditingEntityListener.class)
public class BaseTimeEntity {

    //Entity가 생성될 때 시간이 자동 저장이 된다.
    @CreatedDate
    private LocalDateTime createdDate;

    //조회한 Entity값을 변경할 떄 시간이 자동 저장이 된다.
    @LastModifiedDate
    private LocalDateTime modifiedDate;
}
