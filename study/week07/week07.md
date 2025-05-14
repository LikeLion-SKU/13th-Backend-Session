# Spring Log

## Log

- 프로그램이 실행되면서 남는 기록
    - 사용자가 어떤 요청을 했는지
    - 서버가 어떤 작업을 했는지
    - 오류가 발생했는지
    - 언제 무엇이 일어났는지

## Logging

- 로그를 남기는 행위 또는 기술
- 프로그램이 어떻게 실행되고 있는지에 대한 흔적을 남기는 것

## SLF4J (Simple Logging Facade for Java)

- 자바에서 로깅을 위한 인터페이스만 제공하는 라이브러리
- Facade : 여러 로깅 구현체들을 통합해서 일관된 방식으로 사용하게 해주는 중간 계층 역할
    - 다양한 로깅 라이브러리를 사용 → 코드에 직접 라이브러리API를 치는 대신에 facade 인터페이스를 사용
    - 코드 변경없이 다른 라이브러리의 로깅을 쉽게 전환 가능

## LogBack

- SLF4J의 기본 로깅 구현체
- 우리 코드 → SLF4J(인터페이스) → LogBack(구현체)
    - SLF4J가 어떻게 로그를 남기라고 정해주면, LogBack이 실제 로그를 구현한다

## Log Level

- 로그의 심각도를 구분하기 위한 등급 체계

trace: 가장 상세한 로깅  ex) 반복문 내부 상태 추적

debug: 디버깅 목적의 정보 ex) 메서드 진입, 파라미터 값

info: 일반적인 정상 흐름 ex) 유저 로그인 성공

warn: 경고, 하지만 서비스는 계속 동작 ex) 잘못된 입력

error: 예외 발생, 기능 실패 가능 ex) DB 연결 실패

TRACE < DEBUG < INFO < WARN < ERROR

- 운영 환경에서 너무 많은 로그는 성능을 저하 시킴
    - 보통 INFO 이상만 출력
    - 그 이하도 활성화해서 디버깅 가능
        - application.properties에 logging.level.root=trace 추가 시 5개 다 출력 가능

## Logging 설정 방법

- Logger 객체 선언
    - private static final Logger log = LoggerFactory.getLogger(클래스명.class);
        - 클래스마다 직접 선언해야 하는 단점
    - @SLF4J를 사용해 Logger 객체를 자동으로 생성
        - Lombok 의존성 필수

## 로그 모니터링 방식

- 로그 파일을 통한 방식
- ELK나 Grafana 같은 로그 관리 도구 사용
    - 지금은 오버 스택..
- DB 저장 후 관리
    - 특정 API 호출 시 어떤 에러가 났는지 판단하는 목적이라면 DB사용은 복잡하다고 생각
        - DB는 통계나 분석이 더 용이

### 파일로 로그 관리

- Http 요청마다 로그를 남기기 위한 옵션으로 AOP, Interceptor, Filter 등이 있음
- AOP
    - 장점 : 비즈니스 로직과 별개로 공토으이 관심사를 처리할 수 있어서 깔끔한 코드 분리가 가능함.
    - 단점 : Http 요청 전체를 다루기에는 적합하지 않음
- Filter
    - 장점 : 서블릿 레벨에서 동작, 모든 Http요청에 대해 처리가 가능하기 때문에 가장 하위 레벨에서 로깅이 가능하다
    - 단점 : Sprong MVC 레이어 보다 하위에 위치하므로, Controller와 상호작용이 적기때문에 Controller 관련 정보를 직접적으로 다루기 어려움
- Interceptor
    - 장점 : Spring MVC 레이어에서 Controller에 도달하기 전후에 동작하기 떄문에 요청 전후의 로깅 및 응답시간 측정에 적합하다. Controller로 전달되는 정보를 쉽게 접근할 수 있으며, 요청과 응답을 자유롭게 다룰 수 있음.
    - 단점 : 서블릿 레벨보다는 한 단계 위에서 동작하므로, 정적 리소스나 다른 비즈니스 로직 외의 요청은 처리x