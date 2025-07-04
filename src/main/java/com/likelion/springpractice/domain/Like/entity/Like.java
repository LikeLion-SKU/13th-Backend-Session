package com.likelion.springpractice.domain.Like.entity;

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
@Table(name = "likes")
public class Like extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "food_id", nullable = false)
  private Food food;

  @Column(name = "is_deleted", nullable = false)
  @Builder.Default
  private Boolean isDeleted = Boolean.FALSE;

  public boolean isDeleted() {
    return isDeleted;
  }

  public void softDelete() {
    this.isDeleted = true;
  }

  public void reLike() {
    this.isDeleted = false;
  }
}
