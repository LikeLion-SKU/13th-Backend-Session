package com.likelion.springpractice.domain.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.likelion.springpractice.global.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name="users")
public class User extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name="username", nullable = false)
  private String username;

  @JsonIgnore // 민감한 정보는 넘어가지 않도록 함
  @Column(name="password", nullable = false)
  private String password;

  @Column(name="name", nullable = false)
  private String name;

  @Column(name="name", nullable = false)
  private Nation Nation;

  @Column(name="Introduce")
  private String introduce;

  @Column(name="role", nullable = false)
  @Enumerated(EnumType.STRING)
  @Builder.Default
  private Role role = Role.NONMEMBER;

  @JsonIgnore
  @Column(name="refresh_token")
  private String refreshToken;


  public void saveRefreshToken(String refreshToken) {
    this.refreshToken = refreshToken;
  }



}
