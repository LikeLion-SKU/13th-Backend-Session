package com.likelion.springpractice.week11.dto.request;

import lombok.Getter;

@Getter
public class CreateReviewRequestDto {
    private Long foodId;
    private String content;
    private double rating;
}