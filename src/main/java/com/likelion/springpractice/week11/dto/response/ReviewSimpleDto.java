package com.likelion.springpractice.week11.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ReviewSimpleDto {
    private Long reviewId;
    private String nickname;
    private double rating;
    private String content;
    private LocalDateTime createdAt;
}