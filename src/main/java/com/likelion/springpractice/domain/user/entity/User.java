package com.likelion.springpractice.domain.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.likelion.springpractice.domain.favoritefood.entity.FavoriteFood;
import com.likelion.springpractice.domain.badge.entity.UserBadge;
import com.likelion.springpractice.global.common.BaseTimeEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
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
@Table(name = "users")
public class User extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "username", nullable = false)
  private String username;

  @Column(name = "email", nullable = false, unique = true)
  private String email;

  @JsonIgnore
  @Column(name = "password", nullable = false)
  private String password;

  @Column(name = "language", nullable = false)
  private String language;

  @Column(name = "bio")
  private String bio;

  @JsonIgnore
  @Column(name = "refresh_token")
  private String refreshToken;

  @Column(name = "role", nullable = false)
  @Enumerated(EnumType.STRING)
  @Builder.Default
  private Role role = Role.USER;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<FavoriteFood> favoriteFoods = new ArrayList<>();

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<UserBadge> userBadges = new ArrayList<>();

  public void createRefreshToken(String refreshToken) {
    this.refreshToken = refreshToken;
  }

  public void updateEmail(String newEmail) {
    this.email = newEmail;
  }

  public void updatePassword(String newPassword) {
    this.password = newPassword;
  }

  public void updateUsername(String newUsername) {
    this.username = newUsername;
  }

  public void updateLanguage(String newLanguage) {
    this.language = newLanguage;
  }

}
