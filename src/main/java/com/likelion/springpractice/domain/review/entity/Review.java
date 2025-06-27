package com.likelion.springpractice.domain.review.entity;

import com.likelion.springpractice.domain.food.entity.Food;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Review {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)  // 음식 FK
  @JoinColumn(name = "food_id", nullable = false)
  private Food food;

  private Long userId;

  @Column(nullable = false) // 1~5 맵기
  private int spicinessLevel;

  @Column(nullable = false, length = 255)
  private String content;

  private LocalDateTime createdAt;
  private LocalDateTime modifiedAt;

  @PrePersist
  protected void onCreate() { this.createdAt = LocalDateTime.now(); }

  @PreUpdate
  protected void onUpdate() { this.modifiedAt = LocalDateTime.now(); }

  public void update(int level, String content) {
    this.spicinessLevel = level;
    this.content = content;
  }
}