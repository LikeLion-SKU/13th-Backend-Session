package com.likelion.springpractice.domain.like.entity;


import com.likelion.springpractice.domain.food.entity.Food;
import com.likelion.springpractice.domain.user.entity.User;
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
@Table(name = "likes")
public class Like {


  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "food_id", nullable = false)
  private Food food;

  @Column(name = "like_status", nullable = false)
  @Builder.Default
  private Boolean likeStatus = Boolean.FALSE;

  //좋아요 상태 on이면, 좋아요 끄기
  //좋아요 상태 off이면, 좋아요 키기

  //좋아요 상태를 토글하는 메서드!! ture->false, false->true
  public void toggleLikeStatus() {
    this.likeStatus = !this.likeStatus;
  }

  //좋아요 상태 반환
  public boolean isLiked() {
    return Boolean.TRUE.equals(this.likeStatus);
  }
}
