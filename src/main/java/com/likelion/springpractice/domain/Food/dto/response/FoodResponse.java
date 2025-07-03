package com.likelion.springpractice.domain.Food.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FoodResponse {

  private Long id;
  private String name;
  private String description;
  private double spiciness;
}