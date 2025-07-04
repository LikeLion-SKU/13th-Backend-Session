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
import jakarta.validation.constraints.Email;
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

  @Email
  @Column(name = "email", nullable = false, unique = true) // 이메일 중복 방지
  private String email;   // 아이디(이메일)

  @JsonIgnore
  @Column(name = "password", nullable = false)
  private String password;  // 비밀번호

  @Column(name = "username", nullable = false)
  private String username;  // 닉네임

  @Column(name = "nationality", nullable = false)
  private String nationality; // 국적

  @Column(name = "comment")
  private String comment;   // 자기소개

  @JsonIgnore
  @Column(name = "refresh_token")
  private String refreshToken;

  @Column(name = "role", nullable = false)
  @Enumerated(EnumType.STRING)
  @Builder.Default  // UserService의 User 엔티티 생성하는 부분에서 굳이 명시적으로 role을 넣지 않아도 자동으로 들어가게 해줌
  private Role role = Role.USER;

  public void updateInfo(String name, String password, String nationality) {
    this.username = name;
    this.password = password;
    this.nationality = nationality;
  }

  public void createRefreshToken(String refreshToken) {
    this.refreshToken = refreshToken;
  }

}
