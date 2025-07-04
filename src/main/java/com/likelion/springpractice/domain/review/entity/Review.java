package com.likelion.springpractice.domain.review.entity;

import com.likelion.springpractice.domain.food.entity.Food;
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
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "reviews")
public class Review extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user", nullable = false)
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "food", nullable = false)
  private Food food;

  @Column(name = "spicy_level", nullable = false)
  private int spicyLevel;

  @Column(name = "comment", nullable = false)
  private String comment;

  public void updateReview(int spicyLevel, String comment) {
    this.spicyLevel = spicyLevel;
    this.comment = comment;
  }
}