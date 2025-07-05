package com.likelion.springpractice.domain.mission.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "users") //user테이블 이름은 꼭 users로 하기
public class User extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "username", nullable = false)
  private String username; //사용자 이름(별칭)

  @Column(nullable = false, unique = true)
  private String email; //이메일(아이디)

  @JsonIgnore //실수로 프론트 측에게 보내더라도 넘어가지 않게 하겠다(민감한 정보)
  @Column(name = "password", nullable = false)
  private String password;

  @JsonIgnore
  @Column(name = "refresh_token", nullable = false)
  private String refreshToken;

  @Column(name = "role", nullable = false)
  @Enumerated(EnumType.STRING)
  @Builder.Default
  private Role role = Role.User;

  @Enumerated(EnumType.STRING)
  @Column(name = "country", nullable = false)
  private Country country;

  @Column(name = "introduction")
  private String introduction;

  @Column(name = "review_count", nullable = false)
  @ColumnDefault("0")
  private Integer reviewCount;

  @Builder.Default
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<Grade> grades = new HashSet<>();
  // Hibernate가 List(bag) 타입인 User.grades, User.reviews 두 개를 동시에 fetch join했기 때문에 생긴 문제
  // 그래서 해결 방법으로 컬렉션 하나를 set으로 변경 => 이렇게 하면 Hibernate에서 두 개의 컬렉션을 동시에 fetch join 할 수 있습니다.

  @Builder.Default
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  private List<Review> reviews = new ArrayList<>();

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  @Builder.Default
  private List<Likes> likesList = new ArrayList<>();


  public void createRefreshToken(String refreshToken) {
    this.refreshToken = refreshToken;
  }

  public void createIntroduction(String introduction) {
    this.introduction = introduction;
  }

  public void increaseReviewCount() {
    this.reviewCount += 1;
  }

}
