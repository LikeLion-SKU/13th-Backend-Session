package com.likelion.springpractice.domain.like.entity;

import com.likelion.springpractice.domain.food.entity.Food;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "food_like", uniqueConstraints = @UniqueConstraint(columnNames = {"food_id", "user_id"}))
public class FoodLike {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "food_id", nullable = false)
  private Food food;

  @Column(name = "user_id", nullable = false)
  private Long userId;

  @Column(nullable = false)
  private boolean isLiked;

  @Column(nullable = false)
  private LocalDateTime likedAt;

  public void like() {
    this.isLiked = true;
    this.likedAt = LocalDateTime.now();
  }

  public void cancel() {
    this.isLiked = false;
  }
}