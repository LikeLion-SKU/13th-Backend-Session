package com.likelion.springpractice.domain.mission.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FoodSimpleRequest {

  @NotBlank
  @Schema(example = "치킨")
  private String name;
  
}