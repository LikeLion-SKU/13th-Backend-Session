package com.likelion.springpractice.week11.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FoodLikeResponseDto {
    private Long foodId;
    private boolean liked;
}