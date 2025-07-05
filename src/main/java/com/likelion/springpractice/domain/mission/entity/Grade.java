package com.likelion.springpractice.domain.mission.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Objects;
import java.util.Optional;
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
@Table(name = "grade")
public class Grade {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;


  //후기 개수에 따른 배지 구분
  public static Optional<String> fromReviewCount(Integer reviewCount) {
    if (reviewCount == null || reviewCount < 5) {
      return Optional.empty(); // 기본값
    } else if (reviewCount >= 50) {
      return Optional.of("우리 서버 아이콘배찌");
    } else if (reviewCount >= 30) {
      return Optional.of("수저세트배찌");
    } else if (reviewCount >= 15) {
      return Optional.of("숟가락배찌");
    } else {
      return Optional.of("아기배찌");
    }
  }


  //Set은 내부적으로 중복 제거를 위해 두 메서드를 사용함
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Grade grade = (Grade) o;
    return Objects.equals(name, grade.name) && Objects.equals(user.getId(), grade.user.getId());
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, user.getId());
  }


}
