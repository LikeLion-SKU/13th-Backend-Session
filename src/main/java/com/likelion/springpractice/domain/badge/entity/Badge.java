package com.likelion.springpractice.domain.badge.entity;

import com.likelion.springpractice.domain.MappingUserBadge.entity.MappingUserBadge;
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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "badges")
public class Badge extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long badgeId;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, unique = true)
  private BadgeName badgeName;

  // MappingUserBadge 테이블의 badge 필드와 연관 -> 사용자와 배찌의 다대다 매핑 테이블
  @OneToMany(mappedBy = "badge", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<MappingUserBadge> mappingUsers = new ArrayList<>();

}
