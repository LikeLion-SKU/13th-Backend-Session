package com.likelion.springpractice.global.exception;

import com.likelion.springpractice.global.exception.model.BaseErrorCode;
import com.likelion.springpractice.global.response.BaseResponse;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 커스텀 예외
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<BaseResponse<Object>> handleCustomException(CustomException ex) {
        BaseErrorCode errorCode = ex.getErrorCode();
        log.error("Custom 오류 발생: {}", ex.getMessage());
        return ResponseEntity
            .status(errorCode.getStatus())
            .body(BaseResponse.error(errorCode.getStatus().value(), ex.getMessage()));
    }

    // Validation 실패
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponse<Object>> handleValidationException(
        MethodArgumentNotValidException ex) {
        String errorMessages =
            ex.getBindingResult().getFieldErrors().stream()
                .map(e -> String.format("[%s] %s", e.getField(), e.getDefaultMessage()))
                .collect(Collectors.joining(" / "));
        log.warn("Validation 오류 발생: {}", errorMessages);
        return ResponseEntity.badRequest().body(BaseResponse.error(400, errorMessages));
    }

    //(10주차 과제) 1. 엔드포인트와 일치하지 않는 http method 요청
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<BaseResponse<Object>> handleMethodNotSupported(
        HttpRequestMethodNotSupportedException ex) {
        String message = String.format(
            "지원하지 않는 HTTP 메서드입니다. 요청: %s",
            ex.getMethod()
        );
        log.warn("메서드 미지원: {}", message);
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
            .body(BaseResponse.error(405, message));
    }

    //(10주차 과제) 2. 잘못된 JSON 형식, 파싱 실패
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<BaseResponse<Object>> handleInvalidJson(
        HttpMessageNotReadableException ex) {
        log.warn("잘못된 요청 본문(JSON 파싱 실패): {}", ex.getMessage());
        return ResponseEntity.badRequest().body(BaseResponse.error(400, "요청 본문 형식이 잘못되었습니다."));
    }

    //(10주차 과제) 3. 잘못된 타입의 값 입력
    @ExceptionHandler(TypeMismatchException.class)
    public ResponseEntity<BaseResponse<Object>> handleTypeMismatch(TypeMismatchException ex) {
        String message = String.format("파라미터 타입이 잘못되었습니다: '%s' → %s",
            ex.getValue(),
            ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "알 수 없음");
        log.warn(message);
        return ResponseEntity.badRequest().body(BaseResponse.error(400, message));
    }

    // 예상치 못한 예외
    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse<Object>> handleException(Exception ex) {
        log.error("Server 오류 발생: ", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(BaseResponse.error(500, "예상치 못한 서버 오류가 발생했습니다."));
    }
}
