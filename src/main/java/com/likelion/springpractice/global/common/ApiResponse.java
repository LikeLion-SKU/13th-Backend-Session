package com.likelion.springpractice.global.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 프로젝트 전역에서 사용하는 표준 응답 래퍼.
 * <pre>
 * {
 *     "success": true,
 *     "code": 200,
 *     "message": "요청 성공",
 *     "data": { ... }
 * }
 * </pre>
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

  private boolean success;
  private int code;
  private String message;
  private T data;

  // 성공
  public static <T> ApiResponse<T> ok(String message, T data) {
    return new ApiResponse<>(true, 200, message, data);
  }
  public static <T> ApiResponse<T> ok(String message) {
    return new ApiResponse<>(true, 200, message, null);
  }

  // 실패
  public static <T> ApiResponse<T> error(int statusCode, String message) {
    return new ApiResponse<>(false, statusCode, message, null);
  }
}

