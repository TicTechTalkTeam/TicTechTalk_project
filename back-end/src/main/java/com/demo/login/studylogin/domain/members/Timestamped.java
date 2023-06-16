package com.demo.login.studylogin.domain.members;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Timestamped {
    // 엔터티 객체가 생성될 때 그 시간을 자동으로 채워줌
    // updatable = false는 밑의 수정시간에 따라 생성 시간이 같이 변동되지 않도록 막는 역할을 함
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    // 마지막으로 수정된 시간을 자동으로 채워줌
    @LastModifiedDate
    private LocalDateTime modifiedAt;
}
