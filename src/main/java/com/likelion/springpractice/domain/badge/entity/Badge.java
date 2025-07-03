package com.likelion.springpractice.domain.badge.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "badge")
public class Badge {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long badgeId;

  @Column(name = "badge_name", nullable = false)
  private String badgeName;

  @Column(name = "min_review", nullable = false)
  private int minReview;

  @Column(name = "max_review", nullable = false)
  private int maxReview;

  @OneToMany(mappedBy = "badge", cascade = CascadeType.ALL, orphanRemoval = true)
  @Builder.Default  // 근데 이거 왜 경고 뜨지
  private List<BadgeUser> badgeUsers = new ArrayList<>();

}
