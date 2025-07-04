package com.likelion.springpractice.week11.dto.response;

import com.likelion.springpractice.week11.domain.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReviewResponseDto {
    private Long reviewId;
    private String nickname;
    private String content;
    private double rating;

    public static ReviewResponseDto from(Review review) {
        return new ReviewResponseDto(
                review.getId(),
                review.getUser().getNickname(),
                review.getContent(),
                review.getRating()
        );
    }
}