## log

- 프로그램이 실행되면서 남기는 기록
- 기록되는 내용
    - 요청의 종류, 작업의 종류, 오류 발생 여부 등

## Logging

로그를 남기는 행위 또는 기술

- 소프트웨어 시스템에서 발생한는 모든 행위와 이벤트 정보를 시간 순서대로 기록
- 프로그램의 실행 상태를 모니터링, 문제 진단, 성능 평가에 활용

## SLF4J(Simple Logging Facade for Java)

- 자바에서 로깅을 위한 인터페이스만 제공하는 라이브러리
- Facade(파사드): 여로 로깅 구현체를 통합해서 일관된 방식으로 사용하게 해주는 중간 계층 역할
- 실제 로깅은 구현체가 담당

## LogBack

- SLF4J의 기본 로깅 구현체
- 실제 로깅을 수행

Log Level

| **로그 레벨**   | **설명**               | **예시**         |
|-------------|----------------------|----------------|
| **`TRACE`** | 가장 상세한 로깅. 내부 동작 추적용 | 반복문 내부 상태 추적   |
| **`DEBUG`** | 디버깅 목적의 ㅈ어보          | 메서드 진입, 파라미터 값 |
| **`INFO`**  | 일반적인 정상 흐름           | 일반적인 정상 흐름     |
| **`WARN`**  | 경고. 하지만 서비스는 계속 동작   | 잘못된 입력         |
| **`ERROR`** | 예외 발생, 기능 실패 가능      | DB 연결 실패       |

TRACE < DEBUG < INFO < WARN < ERROR

### 운영 환경

- INFO 이상만 출력
- 너무 많은 로그 → 성능에 악영향

### 개발 환경

- DEBUG 또는 TRACE 까지 활성화해서 디버깅
- 자세한 로그 필요

### 설정하는 법

application.properties에 logging.level.root 속성 값 지정

## logging 설정 방법

### Logger 객체 선언

private static final Logger log = LoggerFactory.getLogger(클래스명.class);

### @Slf4j 사용

Logger 객체를 자동으로 생성하기!

## Controller에선 log 사용 방법

컨트롤러에 직접 로그를 넣는 방식이 아닌

AOP 클래스를 통해 공통 로깅 처리

## AOP(Aspect Oriented Programming: 관점 지향 프로그래밍))

핵심 비즈니스 로직과는 별개의 부가적인 기능들을 효율적으로 분리하여 관리할 수 있게 해주는 프로그래밍

로깅, 성능 모니터링, 입출력 검사 등 여러 분야에서 사용

## **RollingFileAppender**

콘솔에만 로깅하는 것이 아닌 파일에도 따로 로깅을 기록하고 싶을 때 사용하는 appender는 FileAppender이다.

RollingFileAppender는 FileAppender를 상속하여 로그 파일을 rollover한다. rollover는 다음 파일로 이동하는 행위로 한 로그 파일에 무한정
기록할 수 없으니 특정 기준에 따라 기록하는 파일 대상을 바꿔주는 것이다. 해당 appender는 주요 2가지 설정을 해줘야 한다.

- RollingPolicy: rollover에 필요한 action을 설정한다. (ex. TimeBasedPolicy, SizeAndTimeBasedRollingPolicy 등)
- TriggeringPolicy: rollover가 발생하는 기준(정책)을 설정한다.

## Spring Boot에서 Logging 활용-**spring-logback.xml 설정**

로그와 관련된 것들을 설정

콘솔과 파일에 로그를 따로 저장

예시

```java
<? xml version = "1.0"
encoding="UTF-8"?>
<configuration>
    <!--
log 기록
절대 위치
설정 -->
    <property name="LOGS_ABSOLUTE_PATH"value="./logs"/>

    <property name="CONSOLE_LOG_PATTERN"
value="[%d{yyyy-MM-dd HH:mm:ss}:%-3relative]  %clr(%-5level) %clr(${PID:-}){magenta} %clr(---){faint} %clr([%15.15thread]){faint} %clr(%-40.40logger{36}){cyan} %clr(:){faint} %msg%n"/>
    <property name="FILE_LOG_PATTERN"
value="[%d{yyyy-MM-dd HH:mm:ss}:%-3relative] %-5level ${PID:-} --- [%15.15thread] %-40.40logger{36} : %msg%n"/>
              
    <!--

콘솔(STDOUT) -->
    <appender name="STDOUT"class="ch.qos.logback.core.ConsoleAppender">
        <layout class="ch.qos.logback.classic.PatternLayout">

<Pattern> $ {
  CONSOLE_LOG_PATTERN
}</Pattern>
        </layout>
    </appender>

    <!--

파일(FILE) -->
    <appender name="FILE"class="ch.qos.logback.core.rolling.RollingFileAppender">
        <!--
log 기록할
파일 위치
설정 -->

<file> $ {
  LOGS_ABSOLUTE_PATH
}/logback.log</file>
        <!--
log 기록
타입 인코딩 -->
        <encoder>

<pattern> $ {
  FILE_LOG_PATTERN
}</pattern>
        </encoder>
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <!--
daily rollover -->
            <fileNamePattern>logFile .%

d {
  yyyy - MM - dd
}.log</fileNamePattern>
            <!--keep 30 days' worth of history capped at 3GB total size -->
            <maxHistory>30</maxHistory>
            <totalSizeCap>3 GB</totalSizeCap>
        </rollingPolicy>
    </appender>
   
    <!--
log 레벨

설정(logging.level.root=info)-->
    <root level="info">
        <!--
참조할 appender
설정 -STDOUT -->
        <appender-
ref ref = "STDOUT" / >
    </root>

    <!--
log 레벨

설정(org.springframework.web=debug)-->
    <logger name="org.springframework.web"level="debug">
        <appender-
ref ref = "FILE" / >
    </logger>

</configuration>
```

## Spring Boot를 사용하면서 자주 발생하는 오류 로그

`HttpRequestMethodNotSupportedException`

- 지원하지 않는 HTTP 메서드 사용
- POST 요청인데 GET만 지원 등

`HttpMediaTypeNotSupportedException`

- 지원하지 않는 Content-Type 요청
- 서버는 JSON 기대하는데 클라이언트가 XML 전송

`EntityNotFoundException`

- JPA에서 findById().orElseThrow() 등으로 데이터 없음
- 존재하지 않는 ID 조회

`NullPointerException`

- 널 객체 참조
- 객체 초기화 없이 메서드 호출

`AccessDeniedException`

- 권한이 없는 리소스 접근 시 발생
- Spring Security 설정 미흡