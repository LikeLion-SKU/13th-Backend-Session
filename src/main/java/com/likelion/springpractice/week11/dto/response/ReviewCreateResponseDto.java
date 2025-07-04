package com.likelion.springpractice.week11.dto.response;

import java.time.LocalDateTime;

public class ReviewCreateResponseDto {
    private Long reviewId;
    private double rating;
    private String content;
    private LocalDateTime createdAt;
}