package com.likelion.springpractice.domain.badge.entity;

import com.likelion.springpractice.domain.user.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "badge_user")
public class BadgeUser {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long badgeUserId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "badge_id")
  private Badge badge;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;
}
