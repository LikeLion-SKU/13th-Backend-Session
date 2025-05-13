## ✅ 1. 로그(Log)란?

- 프로그램이 실행되며 남기는 **기록**
- 예: 사용자의 요청, 서버 동작, 오류 발생 시간 등
- 즉, **프로그램의 흔적**

---

## ✅ 2. 로깅(Logging)이란?

- 로그를 남기는 행위 또는 기술
- **문제 진단, 디버깅, 성능 모니터링**에 필수
- `System.out.println()`도 로깅의 일종이나, 실무에서는 비효율적

---

## ✅ 3. SLF4J (Simple Logging Facade for Java)

- **로깅 인터페이스만 제공**
- 실제 로깅 구현은 **Logback, Log4j** 등이 수행
- 다양한 로깅 구현체들을 **일관되게 사용할 수 있도록 해주는 파사드(Facade)** 역할

---

## ✅ 4. Logback

- SLF4J의 **기본 구현체**
- Spring Boot의 `spring-boot-starter-logging`에 **기본 포함**
- 별도 설정 없이 바로 사용 가능

- 구조: 우리 코드 → SLF4J 인터페이스 → Logback 구현체

---

## ✅ 5. 로그 레벨 (Log Level)

| 레벨    | 설명                     | 예시               |
|-------|------------------------|------------------|
| TRACE | 가장 상세한 정보 (루프 내부 상태 등) | 반복문 디버깅 등        |
| DEBUG | 개발자용 디버깅 정보            | 파라미터 값, 메서드 진입 등 |
| INFO  | 일반적인 실행 흐름             | 로그인 성공, 요청 처리 등  |
| WARN  | 경고, 실행은 계속됨            | 잘못된 입력 등         |
| ERROR | 오류 발생, 기능 실패 가능        | DB 연결 실패 등       |

> TRACE < DEBUG < INFO < WARN < ERROR

---

## ✅ 6. Logger 설정 방법

```
(1) 직접 선언
private static final Logger log = LoggerFactory.getLogger(클래스명.class);
(2) Lombok 사용 - @Slf4j

@Slf4j // Lombok 어노테이션
public class SampleService {
public void hello() {
log.info("Hello logging");}}
Lombok 의존성 필요
```

---

## ✅ 7. Controller에서의 로깅

- 일반적으로 AOP를 통해 공통 처리

- 컨트롤러 내부 직접 로깅보다는 요청·응답 공통 처리 방식 선호

- 공모전/학습 단계에서는 선택사항

---

## ✅ 8. AOP (Aspect Oriented Programming)

- 로깅, 성능 측정, 입출력 검사 등 부가 기능 분리

- 핵심 로직과 분리해 코드 간결하고 효율적으로 유지

---

## ✅ 요약 정리

- Spring Boot는 SLF4J + Logback 기본 제공

- 로그 레벨로 환경별 출력 조절

- 클래스별로 @Slf4j 어노테이션 사용 추천

- Controller에선 AOP로 공통 로깅 처리

- application.properties에서 로깅 레벨 설정 가능

---

---

## Spring boot 자주 발생하는 오류 로그(5개이상)

## 1️⃣ java.lang.NullPointerException

- **원인**: null 객체를 참조하려고 할 때

- **예시 코드**:

> String name = user.getName(); // user가 null일 수 있음

- **해결**: null 체크 또는 Optional 사용

---

## 2️⃣ java.lang.IllegalArgumentException: id must not be null

- **원인**: findById(null) 등 null ID를 전달한 경우
- **해결**: 메서드 호출 전에 null 체크 필수

---

## 3️⃣ org.springframework.dao.EmptyResultDataAccessException

- **원인**: 없는 ID로 deleteById(id)를 호출했을 때 발생

- **예시 메시지**:

> No class com.example.User entity with id 100 exists!

- **해결**: 삭제 전에 해당 ID의 존재 여부를 확인하거나 예외 처리

---

## 4️⃣ org.springframework.web.HttpRequestMethodNotSupportedException

- **원인**: 잘못된 HTTP 메서드로 요청했을 때 (예: POST만 지원하는 API에 GET 요청)

- **예시 메시지**:

> Request method 'GET' not supported

- **해결**: @PostMapping, @GetMapping 등 메서드와 요청 방식 일치 여부 확인

---

## 5️⃣ org.springframework.beans.factory.NoSuchBeanDefinitionException

- **원인**: Spring Bean으로 등록되지 않은 클래스를 주입하려 할 때

- **예시 메시지**:

> No qualifying bean of type 'com.example.MyService' available

- **해결**: 클래스에 @Service, @Component, @Repository 등의 어노테이션 추가

---