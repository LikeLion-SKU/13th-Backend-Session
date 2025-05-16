<details>
  
  <summary>7주차 미션</summary>

  <details>
    <summary>7주차 정리</summary>

## Log란?
로그는 프로그램이 실행되면서 남기는 기록
- 사용자가 어떤 요청을 했는지
- 서버가 어떤 작업을 했는지
- 오류가 발생했는지
- 언제 무엇이 잉어났는지
**이런 모든 정보들을 기록으로 남기는것**

## Logging이란?
- 이런 로그를 남기는 행위 또는 기술을 로깅(Logging)이라고 함
- 시간 순서대로 기록
- 실행 상태를 모니터링 하고 문제를 진단하며, 성능을 평가하는 데 필수적인 역할
- **쉽게 말해, 로깅은 프로그램이 어떻게 실행되고 있는지에 대한 '흔적'을 남기는것**

## SLF4J(Simple Logging Facade for Java)
- 자바에서 로깅을 위한 인터페이스만 제공하는 라이브러리
- Facade(파사드)란? 여러 로깅 구현체들(Logback, Log4j 등)을 통합해서 일관된 방식으로 사용하게 해주는 중간 계층 역할
- 실제 로깅은 구현체(Logback)가 수행
**로깅 인터페이스를 제공해주면 구현체들이 실제 로직을 담당한다**

### Logback이란?
- SLF4J의 기본 로깅 구현체
- 우리 코드 → SLF4J (인터페이스) → Logback (구현체)
-  SLF4J가 어떻게 로그를 남기라고 정해주면, Logback이 실제로 로그를 남긴다
-  Spring Boot 환경이라면 spring-boot-starter-web > spring-boot-starter-logging에 logback 
구현체가 포함되어 있어 별도의 dependency 추가 없이 사용

### Log Level : 로그의 심각도를 구분하기 위한 등급 체계
- trace: 가장 상세한 로깅  ex) 반복문 내부 상태 추적
- debug: 디버깅 목적의 정보 ex) 메서드 진입, 파라미터 값
- info: 일반적인 정상 흐름 ex) 유저 로그인 성공
- warn: 경고, 하지만 서비스는 계속 동작 ex) 잘못된 입력
- error: 예외 발생, 기능 실패 가능 ex) DB 연결 실패

**TRACE < DEBUG < INFO < WARN < ERROR**
- 운영 환경에서는 너무 많은 로그는 성능에 악영향
→ 그래서 보통 운영에서는 INFO 이상만 출력
- 개발 환경에서는 자세한 로그가 필요
→ DEBUG 또는 TRACE까지 활성화해서 디버깅
- 로그 출력 여부는 현재 로그 레벨 설정에 따라 달라진다(기본 info라면 trace, debug는 안 보임)
- application.properties에 logging.level.root=trace  추가 시 5개 다 출력 가능

## Logging 설정 방법
### Logger 객체 선언
-  private static final Logger log = LoggerFactory.getLogger(클래스명.class);
 → 클래스마다 직접 선언해야 하는 단점
- @Slf4j 사용해 Logger 객체를 자동으로 생성하기!
(@Slf4j를 쓰려면 Lombok 의존성 필수)

### Controller에서 log 사용을 안하나요?
- 주로 입출력을 로깅하긴 하지만 컨트롤러에 직접 로그를 넣는 방식이 아닌 
AOP 클래스를 통해 공통 로깅 처리하는게 일반적!
- 실무에선 이런식으로 사용하지만 우리가 진행하는 프로젝트나 공모전에선
굳이 사용할 필요 없으니 이런게 있다 정도로만 생각하기

## AOP란?
- Aspect Oriented Programming(관점 지향 프로그래밍)
- 핵심 비즈니스 로직과는 별개의 부가적인 기능들을
 효율적으로 분리하여 관리할 수 있게 해주는 프로그래밍
- 로깅, 성능 모니터링, 입출력 검사 등 여러 분야에서 사용

## Spring Boot에서 Logging을 어떤 식으로 활용할까?
- **Spring Boot에서 Logging은 애플리케이션 상태를 추적하고 디버깅하며, 운영 중 오류를 빠르게 파악하기 위해 필수적인 도구입니다.**
- 가장 일반적인 사용 방식은 **Lombok의 @Slf4j 어노테이션**을 사용하는 것이며, Spring Boot는 기본적으로 **SLF4J + Logback** 기반으로 작동합니다.

### 🔧 1. 기본 구조 및 설정
**✅ 기본 로깅 구조**
Spring Boot는 아래 구조로 로깅을 구성합니다:
- SLF4J: 추상화된 인터페이스 (코드에서 사용)
- Logback: 기본 구현체 (logback-classic 라이브러리로 제공)
- 설정파일: application.properties

✅ application.properties 예시
```properties
# 전체 로그 레벨
logging.level.root=INFO

# 패키지별 상세 설정
logging.level.com.example.myapp=DEBUG

# 로그 파일 저장 위치
logging.file.name=logs/app.log

# 로그 포맷
logging.pattern.console=%d{HH:mm:ss} [%thread] %-5level %logger - %msg%n
```
### 🧑‍💻 2. 코드에서 로그 사용 방법
**✅ @Slf4j 사용**
```java
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class LogTestController {

    @GetMapping("/log")
    public String logTest() {
        log.trace("TRACE 레벨 로그");
        log.debug("DEBUG 레벨 로그");
        log.info("INFO 레벨 로그");
        log.warn("WARN 레벨 로그");
        log.error("ERROR 레벨 로그");
        return "로그 테스트 완료";
    }
}
```
- @Slf4j는 자동으로 private static final Logger log = LoggerFactory.getLogger(클래스);를 생성
- 로그 메서드는 상황에 맞는 레벨로 사용해야 함 (info, debug, error 등)

### 📁 3. 로그 레벨 종류 (중요도 순)
| 레벨      | 설명                   |
| ------- | -------------------- |
| `TRACE` | 가장 상세한 로그. 내부 흐름 추적용 |
| `DEBUG` | 디버깅용. 개발 시 유용        |
| `INFO`  | 일반 정보. 운영에서도 출력 가능   |
| `WARN`  | 경고. 잠재적인 문제          |
| `ERROR` | 에러. 예외 발생 등 심각한 문제   |

### 4. 실무에서의 활용 예시
| 목적          | 로그 레벨   | 예시                                   |
| ----------- | ------- | ------------------------------------ |
| 요청 수신 로그    | `INFO`  | `log.info("회원 가입 요청: {}", request);` |
| 디버깅         | `DEBUG` | `log.debug("파라미터 값: {}", param);`    |
| 예외 발생       | `ERROR` | `log.error("DB 에러 발생", e);`          |
| 민감 정보 출력 금지 | -       | **로그에 비밀번호/토큰 절대 출력하지 않기**           |


## 🔍 정리
| 항목       | 설명                                         |
| -------- | ------------------------------------------ |
| 로깅 인터페이스 | SLF4J (`@Slf4j`)                           |
| 기본 구현체   | Logback                                    |
| 설정 방법    | application.properties, logback-spring.xml |
| 사용 목적    | 디버깅, 오류 추적, 운영 로깅                          |
| 운영 환경 팁  | `DEBUG`는 끄고 `INFO` 이상 사용                   |

    
  </details>
  <details>
    <summary>자주 발생하는 오류 로그</summary>

# SpringBoot에서 자주 발생하는 오료 로그 7가지

## ✅ 1. `org.springframework.beans.factory.NoSuchBeanDefinitionException`

### 📌 설명:

빈(Bean)을 찾을 수 없다는 오류.

### 💥 원인:

* `@Component`, `@Service`, `@Repository` 등으로 등록하지 않은 클래스를 주입하려 했을 때
* 잘못된 패키지에 있어서 **컴포넌트 스캔 대상이 아님**

### ✅ 해결:

* 해당 클래스에 `@Component` 또는 유사 어노테이션 붙이기
* `@ComponentScan`의 범위 확인
* 인터페이스를 주입했다면 구현체가 등록되어 있는지 확인

---

## ✅ 2. `java.lang.NullPointerException`

### 📌 설명:

객체가 `null`인데 메서드를 호출하거나 필드에 접근하려고 할 때 발생

### 💥 원인:

* 생성자나 의존성 주입 실패
* Optional 사용하지 않고 null 체크 생략
* Bean 주입 안 됨

### ✅ 해결:

* `@Autowired`가 잘 붙었는지 확인
* 가능하면 `Optional`로 감싸거나 `Objects.requireNonNull()` 사용
* 초기화 안 된 객체 확인

---

## ✅ 3. `org.springframework.http.converter.HttpMessageNotReadableException`

### 📌 설명:

요청 본문(JSON 등)을 객체로 변환할 수 없을 때 발생

### 💥 원인:

* 요청 본문에 JSON 문법 오류 있음
* DTO 클래스에 기본 생성자가 없거나, 필드명이 다름
* enum 변환 실패

### ✅ 해결:

* 요청 JSON 형식과 DTO 필드 일치 확인
* Jackson이 변환 가능한 구조인지 확인
* enum에는 `@JsonCreator`, `@JsonValue` 사용 고려

---

## ✅ 4. `org.springframework.web.HttpRequestMethodNotSupportedException`

### 📌 설명:

지원하지 않는 HTTP 메서드로 요청했을 때

### 💥 원인:

* `@GetMapping`인데 POST로 요청하거나
* 브라우저에서 PUT/DELETE 요청인데 Controller에 해당 메서드가 없음

### ✅ 해결:

* Controller의 `@PostMapping`, `@GetMapping` 등 메서드 확인
* 클라이언트에서 요청 방식 확인 (AJAX, Postman 등)

---

## ✅ 5. `org.springframework.dao.DataIntegrityViolationException`

### 📌 설명:

DB 제약 조건 위반 (NOT NULL, UNIQUE, FK 등)

### 💥 원인:

* 중복된 키 삽입
* NULL 삽입 시도
* 외래키 제약 위반

### ✅ 해결:

* DB 제약 조건 확인
* 입력값 검증(`@Valid`, `@NotNull`) 등으로 사전 방지
* 유니크 조건이면 중복 체크 로직 필요

---

## ✅ 6. `org.springframework.boot.web.server.PortInUseException`

### 📌 설명:

서버를 띄우려 했지만 해당 포트가 이미 사용 중일 때

### 💥 원인:

* 같은 포트로 이미 실행 중인 애플리케이션 존재
* 포트를 닫지 않고 서버 재시작

### ✅ 해결:

* 기존 서버 프로세스 종료 (예: `lsof -i :8080`, `kill -9 <pid>`)
* `application.properties`에서 포트 변경:
  `server.port=8081`

---

## ✅ 7. `org.springframework.web.bind.MethodArgumentNotValidException`

### 📌 설명:

요청 본문 데이터 유효성 검사 실패

### 💥 원인:

* DTO에 `@NotNull`, `@Size` 등 붙여놨는데 값 누락됨
* 컨트롤러에서 `@Valid` 안 붙임

### ✅ 해결:

* DTO에 올바른 validation 어노테이션 적용
* 컨트롤러 파라미터에 `@Valid` 또는 `@Validated` 붙이기
* 전역 예외 처리로 처리 (`@ControllerAdvice`)

---

### 📌 정리 요약

| 오류 로그                                    | 의미           | 주요 원인           | 해결 팁                          |
| ---------------------------------------- | ------------ | --------------- | ----------------------------- |
| `NoSuchBeanDefinitionException`          | Bean을 못 찾음   | 등록 안 됨, 스캔 누락   | `@Component`, `@Service` 등 확인 |
| `NullPointerException`                   | null 접근      | 주입 실패, 초기화 누락   | 주입 여부 확인                      |
| `HttpMessageNotReadableException`        | 요청 본문 파싱 실패  | JSON 형식 오류      | DTO 구조, JSON 일치 확인            |
| `HttpRequestMethodNotSupportedException` | HTTP 메서드 불일치 | Mapping 오류      | Mapping 방식 재확인                |
| `DataIntegrityViolationException`        | DB 제약 위반     | null, 중복, FK 문제 | DB 제약 및 입력값 검증                |
| `PortInUseException`                     | 포트 중복        | 포트 점유 중         | 포트 변경 또는 프로세스 종료              |
| `MethodArgumentNotValidException`        | 유효성 검사 실패    | DTO 어노테이션 미충족   | `@Valid`, `@NotNull` 등 확인     |

---





    
  </details>
</details>
