# 목차

1. 3주차 세션 정리
    1. [SpringBoot Architecture](#springboot-architecture)
    2. [API 명세서](#api-명세서)
    3. [Swagger 실습](#swagger-실습)   
&nbsp;
2. [@Annotation](#annotation)

&nbsp;
# SpringBoot Architecture

`웹 브라우저` ↔ `Tomcat` ↔ `Contoller` ↔ `Service` ↔ `Repository` ↔ `Database`   
>1️⃣부터 8️⃣까지 순서대로 읽어보기!!

&nbsp;
### 🌐 웹 브라우저

사용자가 웹 페이지에서 요청을 전송

1️⃣ 사용자가 웹에서 `/api/users/{userId}`에 요청

8️⃣ 전달받은 결과를 화면에 렌더링

&nbsp;
### 🚪 Tomcat

Spring Boot 안에 내장되어 있는 서버로, 요청 경로에 맞는 컨트롤러로 매핑 시도

2️⃣ 요청을 받고 컨트롤러로 전달

&nbsp;
### 🕹️ Controller (@RestController)

클라이언트와 직접 통신하는 부분으로, API 엔트포인트를 정의하여 요청을 받고 적절한 Service를 호출

ResponseEntity를 통해 응답을 생성하거나 예외 처리 가능

예) `return ResponseEntity.ok(user);`

3️⃣ Service에게 사용자 조회 요청

7️⃣ 클라이언트에게 응답 전송

&nbsp;
### 🛎️ Service (@Service)

비지니스 로직을 처리하는 것을 담당하며, 여러 Repository를 조합하거나 조건 등을 처리

트렌젝션 처리를 위해 `@Transactional`을 사용

4️⃣ Repository에게 DB에서 사용자 조회 요청

6️⃣ 받은 데이터를 가공해 DTO로 변환

&nbsp;
### 🗃️ Repository (@Repository)

DAO(Data Access object) 역할을 하며, 엔티티 객체를 CRUD하는 역할을 수행하는 계층

5️⃣ DB에서 사용자 정보를 가져와서 Service에게 전달

&nbsp;
### 🗄️ Database

실제 데이터가 저장된 곳으로, RDBMS( MySQL 같은 관계형 데이터베이스 관리 시스템)가 SQL 쿼리를 처리하여 데이터 반환

&nbsp;
# API 명세서

### API 명세서란?

클라이언트(프론트엔드)와 서버(백엔드)가 통신할 때, 무엇을 요청해야 어떤 응답이 오는지를 정리해 놓은 설명서

&nbsp;
### 명세서의 작성 방법

| 작성 내용                                                |                                                                                             |
|--------------------|---------------------------------------------------------------------------------------------|
| 📌 기본 정보 | API 이름, 현재 버전, 기본 URL                                                                       |
| 🔒 인증   |                                                                                             |
| 📎 Endpoint & HTTP Method   | `GET /api/users`                                                                            |
| ✏️ 요청 파라미터 설명  | PathVariable, RequestParam(필터링), RequestHeader(Content-Type 등의 메타데이터), RequestBody(전송 데이터)  |
| 📩 응답 구조    | 응답 구조, 상태 코드, 응답 예시                                                                         |
| 🚨 오류 처리   | 상태 코드, JSON 오류 메세지 형식                                                                       |

&nbsp;
### Jackson 라이브러리

Java 객체를 JSON으로 바꾸거나(직렬화) JSON을 Java 객체로 바꾸는 데(역직렬화) 사용되는 라이브러리

🔒 **직렬화 Serializaton**

예) DB에서 가져온 사용자 객체를 프로트로 응답할 때 사용

🔓 **역직렬화 Deserialization**

예) 프론트에서 보내온 회원가입 정보를 자바 객체로 바꿔 처리할 때

&nbsp;
# Swagger 실습

### 1. 의존성 추가

```java
implementation 'org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.1'
implementation 'org.springdoc:springdoc-openapi-starter-webmvc-api:2.8.1'
```

&nbsp;
### 2. 환경 설정 @Configration 작성

✨ `@Value(${server.servlet.context-path:})`

application.properties에 있는 context-path(주소) 값을 주입받음

✨ `public OpenAPI customOpenAPI()`

서버 주소 설정 → JWT 인증 방식 정의 → 문서 정보 설정

✨ `public GroupedOpenApi customGroupedOpenApi()`

Swagger UI 내 API 그룹을 나누는 설정으로, 컨트롤러를 그룹으로 구분해서 관리할 수 있어 용이

&nbsp;
### 3. 컨트롤러 @RestController에 API 작성

✨ HTTP Method인 GET, POST, PUT, PATCH, DELETE 사용하여 작성

| HTTP Method | Annotation | Endpoint |
| --- | --- | --- |
| POST | @PostMapping | /api/create-test |
| GET | @GetMapping | /api/read-test |
| PATCH | @PatchMapping | /api/update-test |
| PUT | @PutMapping | /api/update-test |
| DELETE | @DeleteMapping | /api/delete-test |

&nbsp;
> ❓ `PATCH`와 `PUT`이 같은 URL을 가져도 잘 실행될까?

같은 URL이어도 HTTP Method가 다르기 때문에 Spring이 다른 메서드로 매핑해서 처리하기 때문에 잘 실행된다!

Spring MVC는  HTTP Method 요청이 들어오면 `URL + HTTP Method`를 조합하여 컨트롤러 메서드를 찾기 때문에, 같은 URL이라도 메서드가 다르면 유일성이 보장된다.

> ❓ 브라우저에서 테스트할 때 왜 `GET`만 가능할까?

브라우저 주소창에 단순히 URL만 입력해서 서버에 요청하는 것은 브라우저가 자동으로 GET 요청을 보내기 때문에 `GET` 요청만 가능하다!

&nbsp;
### 4. Swagger UI 접속

`http://localhost:8080/swagger-ui/index.html` 로 접속, 작성한 API 확인 & ‘Try it out’ 눌러 테스트

&nbsp;
# Annotation
# 🕹️ Controller 관련 어노테이션

### `@RestController`

- 기능: 문자나 JSON 같은 데이터를 직접 응답으로 보내주는 컨트롤러
- 사용: 화면(html)을 보여주지 않고, 데이터를 주고받는 API 만들 때 사용

> ❔ 그럼 `@Controller`와 다른 점이 뭘까?
> - `@RestController`는 글자나 JSON을 그대로 응답으로 보내지만,
> - `@Controller`는 html 같은 화면을 보여줄 때 사용


### `@GetMapping` `@PostMapping` `@PutMapping` `@PatchMapping` `@DeleteMapping`

- 기능: HTTP Method 요청을 해당 메서드에 매핑
- 사용: 해당 요청 방식(GET, POST 등)에 맞는 컨트롤러 메서드 위에 붙여서 사용


### `@RequestBody`

- 기능: 요청할 때 보낸 JSON 데이터를 자바 객체로 변경
- 사용: 사용자가 보낸 데이터를 그대로 받아서 처리할 때 사용


### `@RequestParam`

- 기능: 주소 뒤에 붙은 값(?name=나경)을 변수로 받음
- 사용: 검색 등에서 주소에 포함된 값을 받을 때 사용


### `@PathVariable`

- 기능: URL 경로 변수 값을 바인딩
- 사용: `/users/3`처럼 주소 안에 있는 숫자(id) 값을 메서드 파라미터로 받을 때

&nbsp;
# 🛎️ Service 관련 Annotation

### `@Service`

- 기능: 스프링이 관리하는 서비스 계층 빈 등록
- 사용: 비즈니스 로직을 처리하는 클래스 위에 사용


### `@Transactional`

- 기능: 여러 작업을 한꺼번에 처리할 때, 중간에 문제가 생기면 전부 취소(롤백)시켜줌
- 사용: 데이터베이스의 일관성과 안정성을 유지해야 하는 서비스 메서드에 사용


### `@Autowired`

- 기능: 필요한 의존 객체를 자동 주입(DI)
- 사용: 다른 클래스의 빈을 필드, 생성자, 세터로 주입할 때 사용


### `@Value`

- 기능: application.properties 등의 값을 변수에 주입
- 사용: 환경 설정값을 가져올 때 사용

&nbsp;
# 🗃️ JPA 관련 Annotation

### `@Repository`

- 기능: 데이터 접근 객체(DAO)를 스프링 빈으로 등록하고 JPA 예외를 변환
- 사용: 인터페이스형 Repository 클래스에 붙여서 DB 연동 시 사용


### `@Entity`

- 기능: 해당 클래스가 DB 테이블과 매핑되는 JPA 엔티티임을 명시
- 사용: JPA로 관리되는 데이터 객체 클래스 위에 사용


### `@Id`

- 기능: 해당 필드를 기본 키(PK)로 설정
- 사용: 테이블에서 각각의 데이터를 구분하기 위한 번호에 사용


### `@GeneratedValue`

- 기능: 기본 키 값을 자동 생성 (자동 증가 등)
- 사용: `@Id`와 함께 사용하여 ID 자동 생성 전략 지정


### `@Column`

- 기능: 필드를 DB의 컬럼과 매핑하고 속성 설정 (이름, 길이, null 여부 등)
- 사용: 열 이름이나 길이 등을 지정하고 싶을 때 사용


### `@OneToMany` `@OneToOne` `@ManyToMany`

- 기능: 엔티티 간의 연관 관계를 정의
- 사용: Entity 간의 관계 설정 시

&nbsp;
# ✨ Lombok 관련 Annotation

### `@Getter` `@Setter`

- 기능: 해당 필드에 대한 getter/setter 메서드를 자동 생성


### `@NoArgsConstructor` `@AllArgsConstructor` `@RequiredArgsConstructor`

- 기능: 각각 기본 생성자, 전체 필드 생성자, final 필드 생성자를 자동 생성


### `@Builder`

- 기능: 빌더 패턴으로 값을 쉽게 넣어 객체 생성 지원
- 사용: 파라미터가 많은 객체를 유연하게 생성하고 싶을 때


### `@Slf4j`

- 기능: log.info() 같은 로그를 만드는 도구를 자동으로 생성
- 사용: 로그 출력 log.info(), log.error() 등을 위해 사용

&nbsp;
# 🔭 기타 Annotation

### `@Configuration`

- 기능: 설정 정보를 담은 클래스임을 명시
- 사용: Bean 설정, 외부 라이브러리 설정 등을 위한 설정 클래스 위에 사용


### `@Bean`

- 기능: 직접 만든 객체를 스프링 빈으로 등록
- 사용: `@Configuration` 클래스 내부에서 직접 Bean 등록할 때 사용


### `@Valid`

- 기능: 입력한 값이 올바른지 자동으로 검사
- 사용: DTO에서 @NotNull, @Size 등과 함께 사용하여 검증할 때
