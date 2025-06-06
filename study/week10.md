
# 📘 Spring Boot API 응답 통일 및 예외 처리 정리

---

## ✅ 응답 통일 (Response Unification)

### 📌 응답 통일이란?
API가 클라이언트(프론트엔드나 외부 서비스)로 응답을 줄 때, **일관된 형식(JSON 등)** 으로 응답을 주는 작업이다.

- 다양한 API를 호출해도 예측 가능한 구조로 응답을 받을 수 있음
- 사용성과 유지보수성이 향상됨

### 📌 응답 통일 구성 요소
1. **BaseResponse<T>**: 공통 응답 틀 정의
2. **Controller에서 `BaseResponse.success(...)`로 통일**
3. **예외 처리 시 `BaseResponse.error(...)`로 통일**

---

## ⚠️ 프로그램 오류 (Program Error)

### 📌 프로그램 오류란?
프로그램 실행 중 **정상적으로 대처하지 못하는 상황**에서 비정상 종료되거나 에러 팝업이 발생하는 것

### 📌 오류의 종류
| 구분             | 설명                             |
|------------------|----------------------------------|
| Compile Error    | 컴파일 타임의 문법 오류 등       |
| Runtime Error    | 실행 중 발생하는 예외 상황       |
| Logical Error    | 실행은 되나 결과가 잘못된 경우   |

---

## 🧨 예외 처리 (Exception Handling)

### 📌 예외란?
- 실행 중 발생하는 예기치 않은 에러 (Runtime Error)
- 컴파일러는 컴파일 시 문법 오류는 잡지만, **런타임 예외는 잡지 못함**

---

## 🛠️ 예외 처리 방식

### 1. 로컬 예외 처리 (try-catch)
```java
try {
   // 예외 발생 가능 코드
} catch (Exception e) {
   // 예외 처리 로직
}
```

- 간단한 상황에서는 유용
- 중복되기 쉽고 유지보수 어려움

### 2. 전역 예외 처리 (Global Exception Handling)
- 공통 에러 코드 정의
- 한 곳에서 예외 처리 및 응답 형식 통일

#### 구성 요소
- `BaseErrorCode` (interface)
- `GlobalErrorCode` (enum)
- `CustomException` (extends RuntimeException)
- `GlobalExceptionHandler` (with `@RestControllerAdvice`)

---

## 🔄 예외 처리 흐름

### ✅ 정상 흐름

```
Client → Controller → Service → Repository → DB
           ↓
     BaseResponse.success(...)
```

### ❌ 예외 발생 흐름

```
Client → Controller → Service → (Exception 발생)
                                ↓
                    GlobalExceptionHandler
                                ↓
              ErrorCode 기반 BaseResponse 생성
                                ↓
                         JSON 응답 반환
```

---

## 🧩 실제 예시

### [CustomException 사용 예시]
```java
throw new CustomException(GlobalErrorCode.INVALID_INPUT_VALUE);
```

### [GlobalExceptionHandler 처리 예시]
```java
@ExceptionHandler(CustomException.class)
public ResponseEntity<BaseResponse> handleCustomException(CustomException e) {
    return ResponseEntity
        .status(e.getErrorCode().getStatus())
        .body(BaseResponse.error(e.getErrorCode()));
}
```

### [응답 형태 예시 - 클라이언트 수신 JSON]
```json
{
  "success": false,
  "code": 400,
  "message": "유효하지 않은 입력입니다.",
  "data": null
}
```

---

## ✅ 대표적인 예외 처리: 유효성 검사 (@Valid)

---

### 📌 유효성 검사란?

- 사용자가 보낸 데이터가 조건에 맞는지를 검사하는 것
- Spring에서는 클라이언트가 Controller에 요청을 보낼 때,
  요청 본문이 DTO에 매핑되며 유효성 검사도 함께 자동으로 수행됨

```groovy
// Gradle 의존성
implementation 'org.springframework.boot:spring-boot-starter-validation'
```

---

### 📌 유효성 검사 조건

- DTO 클래스에 제약 어노테이션(@NotBlank, @Email 등)이 있어야 함
- Controller 메서드 파라미터에 `@Valid` 사용 필요

```java
@PostMapping("/register")
public ResponseEntity<?> register(@Valid @RequestBody UserDto userDto) {
    ...
}
```

- 조건이 만족되지 않을 경우 `MethodArgumentNotValidException` 발생
  → 전역 예외 처리기로 이동하여 일관된 JSON 응답을 생성

---

### 🔧 자주 사용하는 제약 어노테이션

| 어노테이션       | 설명 |
|------------------|------|
| `@NotNull`       | 해당 필드에 `null`이 들어오면 안 됨 |
| `@NotEmpty`      | `null`, `""`(빈 문자열) 금지 |
| `@NotBlank`      | `null`, `""`, `" "` 모두 금지 (공백 포함 최소 1글자 필수) |
| `@Min(value)`    | 지정된 최소값 이상인지 검사 |
| `@Max(value)`    | 지정된 최대값 이하인지 검사 |
| `@Pattern(...)`  | 정규표현식을 이용한 패턴 검사 |
| `@Email`         | 이메일 형식인지 검사 |
| `@Size(min, max)`| 문자열의 길이를 검사 |

> 📌 `@Size(min = 10, max = 100)`처럼 설정 가능  
> 기본값: `min = 0`, `max = 정수형의 최대값`

---

### 📤 예시: 실패 시 응답(JSON 형태)
```json
{
  "success": false,
  "code": 400,
  "message": "유효하지 않은 입력입니다.",
  "data": null
}
```

---

## ✅ 핵심 요약

| 구성 요소            | 역할 설명 |
|---------------------|------------|
| BaseErrorCode        | 에러 코드 인터페이스 (status, message 정의) |
| GlobalErrorCode      | 실제 코드 정의 enum |
| CustomException      | 예외 발생 시 던지는 사용자 정의 예외 |
| GlobalExceptionHandler | 전역에서 예외를 처리하는 클래스 |
