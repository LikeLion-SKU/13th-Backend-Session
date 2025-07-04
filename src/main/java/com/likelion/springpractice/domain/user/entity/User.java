package com.likelion.springpractice.domain.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.likelion.springpractice.global.common.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "users")
public class User extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "email", nullable = false, unique = true)
  private String email;

  @Column(name = "nickname", nullable = false)
  private String nickname;

  @JsonIgnore
  @Column(name = "password", nullable = false)
  private String password;

  @Column(name = "nation", nullable = false)
  @Enumerated(EnumType.STRING)
  private Nation nation;

  @Column(name = "self_intro", nullable = true)
  private String selfIntro;

  @Column(name = "role", nullable = false)
  @Enumerated(EnumType.STRING)
  @Builder.Default
  private Role role = Role.USER;

  @Column(name = "status", nullable = false)
  @Enumerated(EnumType.STRING)
  @Builder.Default
  private Status status = Status.ACTIVE;

  @JsonIgnore
  @Column(name = "refresh_token", nullable = true)
  private String refreshToken;

  public void removeRefreshToken() {
    this.refreshToken = null;
  }

  public void createRefreshToken(String refreshToken) {
    this.refreshToken = refreshToken;
  }

  public void updateSettings(Nation newNation, String newNickname) {
    this.nation = newNation;
    this.nickname = newNickname;
  }

  public void updateSelfIntro(String newSelfIntro) {
    this.selfIntro = newSelfIntro;
  }
}
