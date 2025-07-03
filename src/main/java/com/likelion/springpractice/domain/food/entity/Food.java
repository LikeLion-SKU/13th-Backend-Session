package com.likelion.springpractice.domain.food.entity;

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
public class Food {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long foodId;

  @Column(name = "foodname", nullable = false)
  private String foodName;

  @Column(name = "description", nullable = false)
  private String foodDescription;

  // 음식에 대한 좋아요, 매운맛 평점, 후기 -> 계산해서 넣자
}
