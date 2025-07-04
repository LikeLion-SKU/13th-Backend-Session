//package com.likelion.springpractice.domain.user.entity;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.likelion.springpractice.global.common.BaseTimeEntity;
//import jakarta.persistence.*;
//import lombok.*;
//
//@Entity
//@Getter
//@Builder
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@AllArgsConstructor
//@Table(name = "users")
//public class User extends BaseTimeEntity {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(name = "username", nullable = false)
//    private String username;
//
//    @JsonIgnore
//    @Column(name = "password", nullable = false)
//    private String password;
//
//    @JsonIgnore
//    @Column(name = "refresh_token")
//    private String refreshToken;
//
//    @Column(name = "role", nullable = false)
//    @Enumerated(EnumType.STRING)
//    @Builder.Default
//    private Role role = Role.USER;
//
//    public void createRefreshToken(String refreshToken) {
//        this.refreshToken = refreshToken;
//    }
//}
