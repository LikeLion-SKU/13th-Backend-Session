package com.likelion.springpractice.domain.own.entity;


import com.likelion.springpractice.domain.badge.entity.Badge;
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
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name="owns")
public class Own  extends BaseTimeEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "state", nullable = false)
  private boolean state;

  // 회원 (외래키 - User 엔티티)
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user", nullable = false) // 외래키 컬럼 이름
  private User user;

  // 배지 (외래키 - Badge 엔티티)
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "badge", nullable = false)
  private Badge badge;


}
