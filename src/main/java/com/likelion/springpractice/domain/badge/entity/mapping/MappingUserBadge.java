package com.likelion.springpractice.domain.badge.entity.mapping;

import com.likelion.springpractice.domain.badge.entity.Badge;
import com.likelion.springpractice.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "mapping_user_badge")
public class MappingUserBadge {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "badge_id", nullable = false)
  private Badge badge;
}