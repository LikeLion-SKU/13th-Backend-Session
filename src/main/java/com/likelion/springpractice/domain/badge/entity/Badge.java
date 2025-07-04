package com.likelion.springpractice.domain.badge.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "badge")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Badge {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "badge_name", nullable = false)
  private String badgeName;

  @Column(name = "description", nullable = false)
  private String description;

  @Column(name = "condition_review_count", nullable = false)
  private int conditionReviewCount;

  @Column(name = "icon_url")
  private String iconUrl;
}