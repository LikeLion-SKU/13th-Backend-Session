package com.likelion.springpractice.domain.badge.entity;

import com.likelion.springpractice.domain.badge.entity.mapping.MappingUserBadge;
import com.likelion.springpractice.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "badges")
public class Badge extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, unique = true)
  private BadgeName badgeName;

  // 매핑 테이블 역참조
  @OneToMany(mappedBy = "badge", cascade = CascadeType.ALL, orphanRemoval = true)
  @Builder.Default
  private List<MappingUserBadge> users = new ArrayList<>();
}