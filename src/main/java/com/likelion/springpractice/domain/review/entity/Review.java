package com.likelion.springpractice.domain.review.entity;

import com.likelion.springpractice.domain.food.entity.Food;
import com.likelion.springpractice.domain.user.entity.User;
import com.likelion.springpractice.global.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
@Table(name = "reviews")
public class Review extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY) // 리뷰 고유 번호
  private Long reviewId;

  @Column(name="score", nullable = false) // 음식 점수
  private int score;

  @Column(name="content", nullable = false) // 음식 리뷰 내용
  private String content;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "userId", nullable = false) // @JoinColumn -> 외래키 컬럼명
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "foodId", nullable = false)
  private Food food;


}
