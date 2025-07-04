package com.likelion.springpractice.domain.badge.entity;

import com.likelion.springpractice.domain.badge.enums.BadgeType;
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

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "badge")
public class Badge extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long badgeId;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, unique = true)
  private BadgeType name;

  @Column(nullable = false)
  private String description;

  @OneToMany(mappedBy = "badge")
  private List<UserBadge> userBadges;


}
