package com.likelion.springpractice.week11.dto.request;

public class ReviewCreateRequestDto {
    private Long foodId;
    private double rating; // 1~5 (맵기 평점)
    private String content;
}