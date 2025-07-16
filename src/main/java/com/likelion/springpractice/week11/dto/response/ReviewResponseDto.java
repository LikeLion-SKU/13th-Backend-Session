package com.likelion.springpractice.week11.dto.response;

import com.likelion.springpractice.week11.domain.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class ReviewResponseDto {
    private Long reviewId;
    private String nickname;
    private String content;
    private double rating;
    private String foodName;
    private LocalDateTime createdAt;

    public static ReviewResponseDto from(Review review) {
        return ReviewResponseDto.builder()
                .reviewId(review.getId())
                .nickname(review.getUser().getUsername())
                .content(review.getContent())
                .rating(review.getRating())
                .foodName(review.getFood().getName())           // 연관된 음식 이름
                .createdAt(review.getCreatedAt())               // 작성일
                .build();
    }
}