package com.likelion.springpractice.domain.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.likelion.springpractice.domain.foodlike.entity.FoodLike;
import com.likelion.springpractice.domain.foodreview.entity.FoodReview;
import com.likelion.springpractice.domain.userbatch.entity.UserBatch;
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
import java.util.ArrayList;
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
@Table(name = "users")
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @JsonIgnore
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "introduce")
    private String introduce;

    @JsonIgnore
    @Column(name = "refresh_token")
    private String refreshToken;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Role role = Role.USER;

    @Column(name = "country", nullable = false)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Country country = Country.KOREA;

    @OneToMany(mappedBy = "user")
    private List<UserBatch> userBatches = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<FoodReview> foodReviews = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<FoodLike> foodLikes = new ArrayList<>();

    public void createRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
