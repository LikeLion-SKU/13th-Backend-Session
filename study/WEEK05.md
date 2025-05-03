## ✅ JPA란?

- **정의:** 자바에서 ORM(Object Relational Mapping) 기술의 **표준 인터페이스 모음**
- **역할:** 객체와 관계형 데이터베이스를 자동으로 매핑해주는 기술

  → SQL 없이 객체로 DB 조작 가능

---

## ✅ JDBC vs JPA

### JDBC

- DB와 통신하기 위한 **기본 API**
- **직접 SQL 쿼리 작성**
- 성능 최적화, SQL 제어에 유리
- 코드 중복 많고 유지보수 어려움

### JPA

- **ORM 기술의 표준**
- 객체 중심으로 DB 조작
- SQL 작성 최소화, CRUD 자동화
- 유지보수 용이

---

## ✅ `@Query`

- **용도:** 정적 쿼리를 메소드 위에 작성
- **주의:** SQL이 아닌 **JPQL** 사용

## 예시

> @Query("SELECT p FROM post p WHERE p.title=:title")
> SESECT*FROM posts WHERE title='찾을 게시글 제목';

## ✅ 사용자 정의 쿼리

- **쿼리 메소드**
- JPA는 메소드 이름으로 쿼리를 생성하는 기능 제공
- 쿼리 메소드는 메소드의 이름을 분석하여 JPGL 쿼리를 실행한다.
- findBy, getBy, readBy, queryBy, searchBy, streamBy등 By는 모두 Select의 일을 하는 Keyword이다.
- And,Or -> method안에 And or을 넣어준다.(findByNameAndEmail)
- After, Before, GreaterThan, LessThan은 값 비교를 해주는 keyword이다.
- After, Before, GreaterThan, LessThan은 초과 미만을 의미하며 Between은 이상, 이하를 의미하는 것을 헷갈리면 안된다.
- isNull은 해당 값에 Null값이 있는지 체크하는 keyword이다.
- NotEmpty는 String과 같은 문자열이 비어있는지 체크가 아닌 Collection type의 변수가 not empty(비어있는지)를 체크한다.
- StartingWith/EndingWith/Contains
- contains("rti")와 like("%rti%")는 같은 것이다
- Sorting은 조건에 따라 데이터의 정렬을 해주는 Keyword이다.(Desc/ Asc로 정렬한다.)

## 예시

> List<Post> findByTitleAndWriter(String title,String writer);
> List<Post> findTop2ByTitle(String title)
> List<User> findByIdBetween(Long id1, Long id2);
> // id가 id1이상, id2이하인 데이터들 return
> List<User> findByIdIsNotNull();
> List<User> findByAddressIsNotEmpty();
> List<User> findTop1ByNameOrderByIdDesc(String name);

## ✅ DTO (Data Transfer Object)

- **역할:** 계층 간 데이터 전달을 위한 객체
- **사용 이유:** 도메인 모델을 직접 노출하지 않기 위해 사용
- `CreatePostRequest`, `UpdateUserResponse` 등 **행위를 붙임**

### 데이터 흐름 구조

# client <-(DTO)->controller  <-(DTO)-> service  <-(DTO)-> Repository  <-(Domain)-> Database

---

## ✅ `@Builder`

- **Lombok 어노테이션**
- 빌더 패턴을 자동 생성

  → 가독성이 좋고, 선택적 파라미터 설정에 유리

---

## ✅ `@Data`

- Lombok에서 자동으로 다음 메소드 생성:
    - `@Getter`, `@Setter`, `@ToString`, `@EqualsAndHashCode`, `@RequiredArgsConstructor`
- **단점:**
    - 불필요한 메소드 생성 가능성
    - 무분별한 Setter 사용
    - `@ToString`에 의한 **순환 참조** 발생 가능 (양방향 연관관계)

---

## ✅ Service 계층

- **가장 복잡한 비즈니스 로직 처리**
- **Controller → Service → Repository** 순으로 호출됨
- 데이터 가공 및 트랜잭션 처리 담당

---

## ✅ 트랜잭션(Transaction)

- **정의:** 데이터 작업의 논리적 단위

  → 하나의 작업처럼 묶어서 처리하며 **중간 오류 발생 시 전부 롤백**

### 💡 예시

> 출금과 입금은 동시에 성공하거나 동시에 실패해야 함

### ✅ 트랜잭션의 4가지 특징 (ACID)

1. **원자성 (Atomicity)**: 전부 실행되거나 전부 취소 (All or Nothing)
2. **일관성 (Consistency)**: 트랜잭션 전후로 DB 상태가 일관됨
3. **독립성 (Isolation)**: 동시에 실행되는 트랜잭션 간 간섭 없음
4. **지속성 (Durability)**: 트랜잭션 성공 시 결과는 **영구 저장**

---

## ✅ Converter

- **정의:** 한 객체를 다른 객체로 변환하는 클래스 또는 메소드
- **용도:** 주로 DTO ↔ Entity 변환

### 사용 방식

1. Service 클래스 내부에 `toEntity()`, `toDto()` 메소드 정의
2. 별도 `converter` 패키지 생성
3. Spring의 `Converter<S, T>` 인터페이스 구현

---

## ✅ ResponseEntity

- **역할:** HTTP 응답 본문 + 상태 코드 제어 가능

### 주요 HTTP 상태 코드

| 코드  | 의미       |
|-----|----------|
| 1xx | 정보 전달    |
| 2xx | 성공       |
| 3xx | 리디렉션     |
| 4xx | 클라이언트 오류 |
| 5xx | 서버 오류    |