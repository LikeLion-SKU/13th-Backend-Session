package com.likelion.springpractice.domain.food.entity;

import com.likelion.springpractice.domain.review.entity.Review;
import com.likelion.springpractice.global.BaseTimeEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
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
  private Long foodId;

  @Column(nullable = false, unique = true)
  private String foodName;

  @Column(nullable = false)
  private String description; // 음식 설명

  @Column(nullable = false)
  private Double rating = 0.0; // 평점

  @Column(nullable = false)
  private int likeNum = 0; // 좋아요 수

  @Column(nullable = false)
  private int reviewNum = 0; // 리뷰 수

  // reviews 테이블과 연관 -> food는 review를 여러개 가짐
  @OneToMany(mappedBy = "food", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Review> reviews = new ArrayList<>();


  public void increaseLikeNum() { // 좋아요 수 증가
    likeNum++;
  }

  public void increaseReviewNum() { // 리뷰 수 증가
    reviewNum++;
  }

  public void update(String food, String description) {
    this.foodName = food;
    this.description = description;
  }

  /*public void caculateRatring() { // 평점 계산 로직

  }*/

}
