package com.likelion.springpractice.domain.Food.entity;

import com.likelion.springpractice.global.common.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "food")
public class Food extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "description", nullable = false)
  private String description;

  @Column(name = "like_count", nullable = false)
  @Builder.Default
  private Integer likeCount = 0;

  @Column(name = "spicy_level_avg", nullable = true)
  private Double spicyLevelAvg;

  // 수정 필요 (동기화 문제가 발생할 수 있을 거 같음)
  public void increaseLikeCount() {
    this.likeCount++;
  }

  public void decreaseLikeCount() {
    if (this.likeCount > 0) {
      this.likeCount--;
    }
  }

  public void updateSpicyLevelAvg(double newAvg) {
    this.spicyLevelAvg = newAvg;
  }

  // 값 변경에 따라 평균값을 계산하거나 증가 감소를 해주는 메소드를 넣으면 동기화 문제가 발생하지 않는지 궁금.
  // 결론: 단순 조회가 많지 않은 필드는 필요할 때마다 계산 처리 로직 사용하는게 좋을 것 같음!
  // 단순 조회가 많은 경우는 다른 필드의 값이 변경되는 순간 갱신해주는 것이 좋을 것 같다
}
