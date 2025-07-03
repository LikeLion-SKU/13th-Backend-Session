package com.likelion.springpractice.domain.review.entity;

import com.likelion.springpractice.domain.food.entity.Food;
import com.likelion.springpractice.domain.user.entity.User;
import com.likelion.springpractice.global.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "review")
public class Review extends BaseTimeEntity {  // 생성일시, 수정일시 때문에 상속 받음

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long reviewId;

  @Column(nullable = false)
  private String content; // 음식에 대한 후기 내용

  @Column(nullable = false)
  private int score;  // 매운맛 별점 1~5


  // 근데 이거 양방향으로 해줘야? -> ㅁㄹ 일단 고민
  @ManyToOne
  @JoinColumn(name = "food_id", nullable = false)
  private Food food;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  public void update(String content, int score) {
    this.content = content;
    this.score = score;
  }
}
