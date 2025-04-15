package com.likelion.springpractice.global;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

// 매번 필드에 추가하지 않고 상속을 받아 자동으로 생성일자와 수정일자를 기록하게 하는 용도
@Getter
@MappedSuperclass // 상속받는 다른 엔티티가 이 클래스의 필드를 공유
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTimeEntity {

  @CreatedDate
  private LocalDateTime createdAt;

  @LastModifiedDate
  private LocalDateTime modifiedAt;
}
