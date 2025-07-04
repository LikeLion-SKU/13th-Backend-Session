package com.likelion.springpractice.week11.domain;

import com.likelion.springpractice.week11.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "nickname", nullable = false, length = 50)
    private String nickname;

    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Column(name = "nationality", length = 50)
    private String nationality;

    @Column(name = "introduction", columnDefinition = "TEXT")
    private String introduction;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "badge_id", nullable = true)
    private Badge badge;

    @Column(name = "withdrawn_at") // 탈퇴일자
    private LocalDateTime withdrawnAt;

    @Column(name = "refresh_token")
    private String refreshToken;

    public void createRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}