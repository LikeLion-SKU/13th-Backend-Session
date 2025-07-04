package com.likelion.springpractice.week11.dto.response;

import com.likelion.springpractice.week11.dto.response.ReviewSimpleDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor // 이게 있어야 Builder도 작동, 직접 생성자 호출도 가능
public class FoodDetailResponseDto {

    private Long id;
    private String name;
    private String description;
    private double averageRating;
    private int likeCount;
    private List<ReviewSimpleDto> reviews;
}