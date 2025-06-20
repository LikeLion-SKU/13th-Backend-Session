package com.likelion.springpractice.domain.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.likelion.springpractice.domain.review.entity.Review;
import com.likelion.springpractice.global.BaseTimeEntity;
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
  @GeneratedValue(strategy = GenerationType.IDENTITY) // 아이디 고유 번호
  @Column(name = "id")
  private Long userId;

  @Column(name="username", nullable = false) // 사용자 아이디
  private String username;

  @JsonIgnore // 민감한 정보는 넘어가지 않도록 함
  @Column(name="password", nullable = false)
  private String password;

  @Column(name="name", nullable = false) // 사용자 이름
  private String name;

  @Column(name="nation")
  @Enumerated(EnumType.STRING)
  private Nation nation;

  @Column(name="introduce")
  private String introduce;

  // reviews 테이블과 연관 -> user는 review를 여러개 가짐
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Review> reviews = new ArrayList<>();


  @Column(name="role", nullable = false)
  @Enumerated(EnumType.STRING)
  @Builder.Default
  public Role role = Role.NONMEMBER;

  @JsonIgnore
  @Column(name="refresh_token")
  private String refreshToken;


  public void saveRefreshToken(String refreshToken) {
    this.refreshToken = refreshToken;
  }



}
