package com.likelion.springpractice.domain.like.exception;

import com.likelion.springpractice.global.exception.model.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum LikeErrorCode implements BaseErrorCode {
  FOOD_NOT_FOUND("LIKE_404_01", "해당 음식을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
  ALREADY_LIKED("LIKE_400_01", "이미 좋아요가 등록되어 있습니다.", HttpStatus.BAD_REQUEST),
  LIKE_NOT_FOUND("LIKE_404_02", "좋아요 내역이 없습니다.", HttpStatus.NOT_FOUND);

  private final String code;
  private final String message;
  private final HttpStatus status;
}