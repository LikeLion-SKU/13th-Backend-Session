# Spring Boot (Week 7) - Logging

---

## Log
- 프로그램 실행 중 남는 **기록**
- 어떤 요청이 있었는지, 어떤 작업이 이루어졌는지, 오류가 발생했는지, 언제 일어났는지를 기록
- **흔적(trace)** 을 남겨 프로그램 상태를 파악하는 데 사용

---

## Logging
- 로그를 **남기는 행위나 기술**
- 소프트웨어에서 발생하는 모든 동작과 이벤트 정보를 **시간 순서대로 기록**
- 프로그램의 상태를 **모니터링**, **문제 진단**, **성능 평가**에 사용됨
- 간단한 예: `System.out.println()` 도 로깅의 일환

---

## SLF4J (Simple Logging Facade for Java)
- Java에서 로깅을 위한 **인터페이스만 제공하는 라이브러리**
- **Facade 패턴**: Logback, Log4j 같은 구현체들을 **일관된 방식**으로 사용할 수 있게 해줌
- 실제 로깅은 Logback, Log4j 등의 구현체가 수행

---

## Logback
- SLF4J의 **기본 로깅 구현체**
- 동작 흐름:  
  `우리 코드 → SLF4J(인터페이스) → Logback(구현체)`
- Spring Boot에서는 `spring-boot-starter-logging`에 포함되어 있어 별도 의존성 추가 없이 사용 가능

---

## Log Level (로그 레벨)

로그의 중요도와 상세함을 나누는 등급 체계:

| 레벨   | 설명 |
|--------|------|
| `TRACE` | 가장 상세한 로그 (ex. 반복문 내부 상태 추적) |
| `DEBUG` | 디버깅 정보 (ex. 메서드 진입, 파라미터 값) |
| `INFO`  | 일반적인 흐름 (ex. 로그인 성공) |
| `WARN`  | 경고이지만 작동함 (ex. 잘못된 입력) |
| `ERROR` | 예외, 기능 실패 가능 (ex. DB 연결 실패) |

> TRACE < DEBUG < INFO < WARN < ERROR

### 환경별 로그 레벨 사용 기준
- 운영 환경: `INFO` 이상만 출력 → 너무 많은 로그는 성능 저하 유발
- 개발 환경: `DEBUG` 또는 `TRACE`까지 활성화하여 디버깅

---

## Spring Boot에서의 Logging 활용

### 1. Logger 선언 방법

```java
// 기본 선언 방식
private static final Logger log = LoggerFactory.getLogger(클래스명.class);
```

### 2. Lombok 활용 방식

```java
@Slf4j
public class SampleService {
    public void test() {
        log.info("정보 출력");
        log.error("에러 발생");
    }
}
```

> `@Slf4j` 사용 시 `Lombok` 의존성 필요

---

### 3. application.properties 설정 예시

```properties
# 전체 루트 로거 레벨
logging.level.root=INFO

# 특정 패키지에 대한 로그 레벨 지정
logging.level.com.example=DEBUG
```

---

## Controller에서의 Logging

- Controller에는 **직접 log를 넣기보단**,  
  **AOP(Aspect Oriented Programming)** 를 통해 공통 로그 처리하는 방식이 일반적

### 예시
- 요청 시작 시점 로그
- 파라미터 로깅
- 응답 시간 로깅 등

---

## AOP

**Aspect Oriented Programming (관점 지향 프로그래밍)**  
핵심 비즈니스 로직과는 별개의 공통 기능(관심사)을 분리하여 효율적으로 관리할 수 있게 해줌

### 사용 예
- 로깅
- 성능 모니터링
- 입력값 검증
- 보안 처리 등

---


# ❗ Spring Boot에서 자주 발생하는 오류 로그 정리

---

## 1) `java.util.NoSuchElementException: No value present`
- **원인**: `Optional.get()` 호출 시 값이 없을 경우 발생
- **예시**
  ```java
  Optional<User> user = userRepository.findById(1L);
  User result = user.get(); // No value present
  ```
- **해결 방법**: `orElse()`, `orElseThrow()` 또는 `isPresent()`로 체크

---

## 2) `org.springframework.beans.factory.NoSuchBeanDefinitionException`
- **원인**: 스프링 컨텍스트에 없는 Bean을 주입하려 할 때
- **예시**
  ```
  No qualifying bean of type 'com.example.MyService' available
  ```
- **해결 방법**: `@Service`, `@Component`, `@Repository` 누락 여부 확인

---

## 3) `org.springframework.dao.EmptyResultDataAccessException`
- **원인**: 기대한 결과 수와 실제 결과 수가 다름 (예: 1개 기대, 0개 반환)
- **예시**
  ```
  Expected one result (or null) to be returned by selectOne(), but found: 0
  ```
- **해결 방법**: `Optional`로 null-safe 처리

---

## 4) `org.springframework.web.HttpRequestMethodNotSupportedException`
- **원인**: 지원하지 않는 HTTP 메서드 요청
- **예시**
  ```
  Request method 'POST' not supported
  ```
- **해결 방법**: 요청 방식과 `@GetMapping`, `@PostMapping` 일치 여부 확인

---

## 5) `org.springframework.http.converter.HttpMessageNotReadableException`
- **원인**: 잘못된 JSON 형식, 역직렬화 실패
- **예시**
  ```
  Cannot deserialize value of type `java.time.LocalDateTime` from String "2023-99-99T99:99"
  ```
- **해결 방법**: JSON 값 확인, Jackson 포맷 지정

---

## 6) `org.springframework.web.bind.MissingServletRequestParameterException`
- **원인**: `@RequestParam` 필수 파라미터 누락
- **예시**
  ```
  Required String parameter 'id' is not present
  ```
- **해결 방법**: `required = false` 설정, 요청 파라미터 확인

---

## 7. `java.lang.NullPointerException`
- **원인**: null 객체에 메서드나 필드 접근 시
- **예시**
  ```
  Cannot invoke "com.example.User.getName()" because "user" is null
  ```
- **해결 방법**: null 체크, Optional 사용, 의존성 주입 확인

---

## 8. `org.hibernate.LazyInitializationException: could not initialize proxy`
- **원인**: 트랜잭션 외부에서 Lazy 로딩된 엔티티에 접근
- **예시**
  ```
  failed to lazily initialize a collection of role: com.example.User.posts
  ```
- **해결 방법**: `@Transactional` 내부에서 처리, EAGER로 변경 또는 DTO로 변환

---
