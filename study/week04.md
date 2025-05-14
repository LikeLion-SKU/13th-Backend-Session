# Spring Boot (Week 4)

# 🌱 Spring Data JPA 및 스프링 구성 요소 개념 정리

---

## 의존성 - Spring Data JPA

- 현대 웹 애플리케이션에서 관계형 데이터베이스(RDB)는 필수 요소
- 객체를 RDB에서 관리하는 것이 매우 중요함
- 하지만 프로젝트가 SQL 위주로 작성되면 코드 품질 저하 및 유지보수 어려움 발생

---

## RDB와 객체지향의 패러다임 충돌

- RDB: **데이터 저장 방식 중심** 기술
- 객체지향 언어: **기능과 속성 통합 중심** 기술
- 서로 패러다임이 달라 객체를 RDB에 저장하려 할 때 불일치 문제 발생

---

## JPA의 역할

- 개발자는 **객체지향**적으로 프로그래밍
- JPA는 이를 RDB에 맞게 **자동으로 SQL 생성 및 실행**
- → **SQL에 종속되지 않는 개발** 가능

---

## Lombok의 의존성

- 자바 클래스 생성 시 반복되는 코드 많음 → 실수, 작업량 증가
- 대부분 **의미 없는 반복 작업** (getter, setter, constructor 등)

---

## Lombok

- **어노테이션 기반 코드 자동 생성 라이브러리**
- 주요 자동 생성 메서드:
    - 생성자
    - `getName()`, `setAge()`, `toString()`, `equals()` 등
- → **코드 간결화** 및 **생산성 향상**

---

## 스프링부트 기본 구조

```
Client(browser)
   ↓    ↑
 Controller
   ↓    ↑
  Service
   ↓    ↑
Repository
   ↓    ↑
 Entity ↔ Database
```

- 계층 간 DTO를 통해 데이터 전달
- Repository는 DB와 직접 연결되고 Entity를 다룸

---

## Domain

- 게시글, 회원, 댓글, 결제 등 **요구사항 혹은 문제 영역**
- 즉 **서비스의 핵심 주제이자 비즈니스 영역**

---

### Domain 구성 요소

- **Entity**: Post, Comment, User 등 DB와 매핑되는 객체
- **Repository**: 데이터 저장/조회 역할
- **Business Logic**: 도메인 관련 로직 처리

> 도메인은 비즈니스 중심의 코드들이 모이는 공간

---

## Entity 클래스

- 실제 DB의 테이블과 매핑되는 클래스
- 반드시 `@Entity` 어노테이션 필요
- 해당 클래스의 인스턴스가 JPA에 의해 관리됨

### ⚠️ 기본 키 설정

- 기본 키 지정 시 `@Id` 어노테이션 사용
- **Primary Key / Foreign Key** 개념은 필수 학습 항목

---

## BaseTimeEntity

- JPA에서 공통으로 사용하는 필드인 생성 시간(`createdDate`)과 수정 시간(`modifiedDate`)을 자동 관리하는 **추상 클래스**
- 여러 엔티티에서 상속받아 반복 제거

---

# 🧩 Spring Annotation & Swagger 정리

---

## 클래스 레벨 어노테이션 (공통)

- `@MappedSuperclass`  
  상속받는 엔티티 클래스에서 필드가 테이블 컬럼으로 인식되게 함

- `@EntityListeners(AuditingEntityListener.class)`  
  생성/수정 시점 콜백을 처리할 수 있는 리스너 등록

- `@CreatedDate`  
  처음 저장될 때 자동으로 현재 시간 저장

- `@LastModifiedDate`  
  엔티티가 업데이트 될 때마다 현재 시간 자동 설정

- ⚠️ 주의사항:  
  Main 클래스에 반드시 `@EnableJpaAuditing` 추가 필요

---

## 클래스 레벨 어노테이션 (JPA + Lombok)

- `@Entity`  
  이 클래스가 JPA 엔티티임을 나타냄, DB 테이블과 매핑됨

- `@Getter`  
  모든 필드의 getter 자동 생성

- `@Builder`  
  빌더 패턴 지원 (`Post.builder().title().content().build()`)

- `@NoArgsConstructor`  
  파라미터 없는 기본 생성자 생성 (JPA에서 필수)

- `@AllArgsConstructor`  
  모든 필드 값을 인자로 받는 생성자 자동 생성

- `@Table(name = "posts")`  
  DB에 매핑될 테이블명을 지정

---

## 필드 레벨 어노테이션

- `@Id`  
  엔티티의 기본 키 지정

- `@GeneratedValue(strategy = GenerationType.IDENTITY)`  
  auto_increment 방식으로 기본 키 자동 생성

- `@Column(length = 500, nullable = false)`  
  컬럼 속성 지정 (길이, null 여부 등)

---

## Controller

- 사용자의 요청(Request)을 받아서 서비스 로직을 호출하고,  
  결과를 응답(Response)으로 반환하는 역할

---

## Controller 관련 어노테이션

- `@RequiredArgsConstructor`  
  필요한 필드만 포함한 생성자 자동 생성 (DI를 위함)

- `@RestController`  
  REST API 요청을 처리하는 컨트롤러 지정

- `@RequestMapping("/api/v1")`  
  해당 클래스의 모든 메서드에 공통 URL prefix 부여

---

## 파라미터 레벨 어노테이션

- `@RequestBody`  
  클라이언트의 JSON 요청을 자바 객체로 매핑

- `@PathVariable`  
  URL 경로 변수 값을 자바 변수로 추출

---

## Swagger 명세서 작성 요령

### 명세서 작성 위치
- **Controller**: API 기능 설명
- **DTO**: 요청/응답 데이터 구조 설명

---

## Swagger 어노테이션

- `@Tag`  
  API 카테고리 설명

- `@Operation`  
  해당 API 기능에 대한 설명

- `@Parameter`  
  개별 파라미터에 대한 설명

- `@Schema`  
  DTO 필드 설명 + 예시값(example) → Swagger UI에서 자동 반영

---