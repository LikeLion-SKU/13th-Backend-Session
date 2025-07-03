package com.likelion.springpractice.domain.like.entity;

import com.likelion.springpractice.domain.food.entity.Food;
import com.likelion.springpractice.domain.user.entity.User;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
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
@Table(name = "likes")
public class Like {

  @EmbeddedId
  private LikeId id;  // 외래키 조합을 기본키로 사용

  @ManyToOne
  @MapsId("userId") // LikeId 의 userId와 매핑
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @ManyToOne
  @MapsId("foodId") // LikeId 의 foodId와 매핑
  @JoinColumn(name = "food_id", nullable = false)
  private Food food;

  public static Like of(User user, Food food) {

    return new Like(new LikeId(user.getId(), food.getFoodId()), user, food);
  }
}
