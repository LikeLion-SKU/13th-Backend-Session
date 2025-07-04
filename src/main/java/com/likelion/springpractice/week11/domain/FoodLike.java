package com.likelion.springpractice.week11.domain;

import com.likelion.springpractice.week11.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "food_like", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "food_id"})
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class FoodLike extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_id", nullable = false)
    private Food food;

    @Setter
    @Column(name = "liked_at", nullable = false)
    private LocalDateTime likedAt;

    @Setter
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

}