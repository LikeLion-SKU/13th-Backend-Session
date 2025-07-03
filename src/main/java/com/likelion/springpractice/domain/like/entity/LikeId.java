package com.likelion.springpractice.domain.like.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable // 이거 외래키 조합으로 기본키 만들때 필요
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class LikeId {

  private Long userId;  // 사용자 고유 번호
  private Long foodId;  // 음식 고유 번호
}
