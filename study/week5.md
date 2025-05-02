<details>
  
  <summary>5주차 미션</summary>

  <details>
    <summary>Repository</summary>

    
    
## Repository
- database와 통신하는 계층
- JPA를 상속받음으로써 기본적인 CRUD 동작(함수 사용)이 가능해짐
  
**Client <->(DTO) Controller <->(DTO) Service <->(DTO) Repository <->(Domain) Database**

## JPA
- 자바에서 ORM 기술의 표준으로 사용되는 인터페이스의 모음
- JPA를 구현한 구현체 : **Hibernate**, EclipseLink 등

## ORM이란?
- Object Relation Mapping(객체 - 관계형데이터베이스의 매핑)
- 관계형 데이터베이스의 객체를 자동으로 연결해주는 기술
- CRUD SQL 쿼리문을 작성하지 않아도 DB와 상호작용하도록 해줌

## JDBC vs JPA
### JDBC
- 데이터베이스와 통신하기 위한 API
- 개발자가 SQL 쿼리문을 직접 작성
- 성능 최적화, SQL 제어에 유리
- 유지보수가 어려움
- 코드가 반복됨

### JPA
- ORM 기술의 표준으로 사용되는 인터페이스
- 객체(Entity)를 통해 데이터 조작
- 복잡한 매핑 작업에 유리
- JPQL 사용
- 유지보수 쉬움, CRUD 자동화

**JDBC와 JPA는 아예 다른게 아님! - JPA 안에 JDBC(JDBC API)가 있음**

### JPQL
- JPA에서 사용하는 객체지향 쿼리 언어
- 객체를 기준으로 하므로 DB 구조가 바뀌어도 유연함
- SQL : 테이블명, 컬럼명을 기준으로 작성  <->  JPQL : 엔티티명, 필드명을 기준으로 작성

### 쿼리메소드(사용자 정의 쿼리)
- JPA는 메소드 이름으로 쿼리를 생성하는 기능 제공
- 쿼리 메소드는 메소드의 이름을 분석하여 JPQL 쿼리를 실행함

### 사용자 정의 쿼리(@Query)
- 실행할 메소드 위에 정적 쿼리를 작성할 때 사용
- SQL이 아닌 JPQL 쿼리가 들어가야함
<details>
    <summary>JPQL 문법</summary>

✅ JPQL 기본 개념
**SQL은 데이터베이스 테이블(Table)을 대상으로 함**

**JPQL은 자바의 엔티티(Entity)를 대상으로 함**

예시 비교:

```SQL
SELECT * FROM post (SQL)     	SELECT p FROM Post p (JPQL)
 
SELECT * FROM post WHERE title='a' (SQL)   	SELECT p FROM Post p WHERE p.title = 'a'  (JPQL)
```
### 1. SELECT문
```java
@Query("SELECT p FROM Post p")
List<Post> findAllPosts();
```
- Post는 엔티티 클래스의 이름
- p는 Post의 별칭(alias)

### 2. WHERE 조건절
```java
@Query("SELECT p FROM Post p WHERE p.title = :title")
List<Post> findByTitle(@Param("title") String title);
```
**:title은 바인딩 변수 (파라미터)** => 나중에 값 들어오면 p.title = "제목"
### 3. ORDER BY 정렬
```java
@Query("SELECT p FROM Post p ORDER BY p.createdAt DESC")
List<Post> findAllOrderByCreatedAtDesc();
```
### 4. COUNT, SUM 등 집계
```java
@Query("SELECT COUNT(p) FROM Post p")
long countAllPosts();
```
### 5. JOIN 문법
```java
@Query("SELECT p FROM Post p JOIN p.author a WHERE a.name = :name")
List<Post> findByAuthorName(@Param("name") String name);
```
- Post 엔티티가 @ManyToOne User author 같은 필드를 가지고 있어야 함

### 6. LIKE 검색
```java
@Query("SELECT p FROM Post p WHERE p.title LIKE %:keyword%")
List<Post> searchByTitle(@Param("keyword") String keyword);
```
## ✅ 주의사항
테이블명 사용 ❌	:  post(테이블명) 사용하면 안 됨. Post(엔티티명) 사용해야 함

컬럼명 사용 ❌	:  title, content는 필드 이름 기준

</details>
</details>
<details>
  <summary>DTO</summary>

## DTO
- Data Transfer Object, 데이터 전송 객체
- 계층 간 데이터 전송을 위해 도메인 모델(entity) 대신 사용되는 객체
- Request, Response에는 꼭 행위(Create, Update, Get)를 붙인다.
- 파일 정렬등을 고려하면 도메인명을 앞으로 빼도 무방하다.

### BUilder
@BUilder : Builder를 자동으로 생성해주는 어노테이션

### @Data
- 다음의 어노테이션과 다양한 메소드를 자동 생성해줌
- @Getter, @Setter, @ToString, @EqualsAndHashCode, @RequiredArgsConstructor

### @Data를 지양해야 하는 이유
1. 불필요한 메소드 생성
  - equlas, hashCode 메소드 등 불필요한 메소드가 생성
2. 무분별한 Setter 남용
  - Setter는 객체를 언제든지 변경할 수 있는 상태로 만듬
  - 객체의 안정성을 보장받기 어려움
3. ToString으로 인한 양방향 연관관계시 순환 참조 문제
  </details>
  

  
  <details>
    <summary>Service</summary>

## Service
- 비즈니스 로직을 담당, 가장 복잡한 코드가 들어가는 부분
- Controller에서는 Service 메소드를 호출, Service에서는 Repository 메소드를 호출함

### 어노테이션
- @Service : 비즈니스 로직을 수행하는 서비스 레이어 클래스임을 나타냄
- @Transactional : 선언적 데이터베이스 트랜잭션 관리 방법 제공
  => public 메소드에만 적용
  
  => 같은 객체 내 다른 메소드에서 호출 시, 트랜잭션 적용 X -> Spring AOP 때문!

### 트랜잭션
- 데이터 거래에 있어서 안정성을 확보하기 위한 방법
- 여러개의 작업을 하나로 묶어서 처리하는 방법
- 데이터 처리 과정에서 오류 발생 -> 모든 작업을 원 상태로 복구함(Rollback)
- 처리 과정이 모두 성공 -> 결과를 반영함(Commit)
- 동시에 성공하거나 동시에 실패해야함
- 데이터 처리 과정을 묶은것을 트랜잭션이라고 함

### 트랜잭션의 특징(ACID)
- A(원자성)(Atomicity) : 트랜잭션은 데이터베이스에서 모두 반영하거나 모두 반영되지 않아야한다.
- C(일관성)(Consistency) : 트랜잭션이 시작하기 전과 끝난 후에도 데이터베이스는 일관된 상태로 유지해야 한다.
- I(독립성)(Isoloation) : 여러 트랜잭션이 동시에 실행될 때, 각 트랜잭션은 다른 트랜잭션의 작업에 영향을 받지 않아야 한다.
- D(지속성)(Durability) : 트랜잭션이 성공적으로 완료되었을 때 결과는 영구적으로 반영되어야 한다.

##  Converter
- 한 객체를 다른 객체로 바꾸는 역할을 하는 클래스나 메소드
- DTO <-> Entity 변환에 주로 사용
- Service 클래스 내부에 Converter 메소드 생성
- Converter 패키지 생성 후 Converter 클래스 생성

## ResponseEntity
- **개발자가 직접 결과 데이터와 HTTP 상태 코드를 제어할 수 있는 클래스**
- 결과값, 상태코드, 헤더값을 모두 프론트에 넘겨줄 수 있고, 에러코드 또한 섬세하게 설정하여 넘길 수 있음

### HTTP 응답 상태 코드
- 1xx : 정보 전달
- 2xx : 성공
- 3xx : 리디렉션
- 4xx : 클라이언트 오류
- 5xx : 서버 오류

## ReponseEntity의 메소드들
### ResponseEntity.ok()
- 200 OK 응답 반환
- 요청이 정상적으로 처리되었음을 나타냄
- 200 상태코드와 함께 body 반환 가능 => return ResponseEntity.ok(dto);

### ResponseEntity.notFound()
- 404 Not Found 응답 반
- 요청한 리소스를 찾을 수 없을 
- body 전송 불가
- return ResponseEntity.notFound().build();

</details>
</details>
