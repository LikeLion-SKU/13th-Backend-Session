package com.likelion.springpractice.domain.Review.entity;

import com.likelion.springpractice.domain.Food.entity.Food;
import com.likelion.springpractice.domain.user.entity.User;
import com.likelion.springpractice.global.common.BaseTimeEntity;
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
@Table(name = "review")
public class Review extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "food_id", nullable = false)
  private Food food;

  @Column(name = "content", nullable = true)
  private String content;

  @Column(name = "spicy_level", nullable = false)
  private Double spicyLevel;

  @Column(name = "is_deleted", nullable = false)
  @Builder.Default
  private Boolean isDeleted = Boolean.FALSE;

  public void updateReview(String content, Double spicyLevel) {
    this.content = content;
    this.spicyLevel = spicyLevel;
  }

  public void softDelete() {
    this.isDeleted = true;
  }
}
