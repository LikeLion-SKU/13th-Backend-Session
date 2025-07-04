package com.likelion.springpractice.domain.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.likelion.springpractice.domain.badge.entity.UserBadge;
import com.likelion.springpractice.domain.like.entity.Like;
import com.likelion.springpractice.domain.review.entity.Review;
import com.likelion.springpractice.domain.user.enums.Language;
import com.likelion.springpractice.domain.user.enums.Role;
import com.likelion.springpractice.global.common.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
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
@Table(name = "users")
public class User extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long userId;

  @Column(nullable = false, unique = true)
  private String username; // 이메일

  @JsonIgnore
  @Column(nullable = false)
  private String password;

  @Column(nullable = false)
  private String name;

  @Enumerated(EnumType.STRING)
  private Language language;

  private String introduction;

  @Builder.Default
  @Column(nullable = false)
  private Integer reviewCount = 0;

  @Column(name = "role", nullable = false)
  @Enumerated(EnumType.STRING)
  private Role authRole;

  @JsonIgnore
  @Column(name = "refresh_token")
  private String refreshToken;

  @OneToMany(mappedBy = "user")
  private List<Review> reviews;

  @OneToMany(mappedBy = "user")
  private List<Like> likes;

  @OneToMany(mappedBy = "user")
  private List<UserBadge> userBadges;

  public void createRefreshToken(String refreshToken) {
    this.refreshToken = refreshToken;
  }

}
