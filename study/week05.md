# Repository

database와 통신하는 계층

JPA를 상속받음으로써 기본적인 CRUD 동작(함수 사용)이 가능해짐

## ORM(Object Relational Mapping)

- 관계형 데이터베이스와 객체를 자동으로 연결해주는 기술
- CRUD SQL 쿼리문을 작성하지 않아도 DB와 상호작용하도록 해줌

## JDBC

- 데이터베이스와 통신하기 위한 API
- 개발자가 SQL 쿼리 문을 직접 작성
- 성능 최적화, SQL 제어에 유리
- 유지보수가 어려움
- 코드가 반복됨

## JPA

- ORM 기술의 표준으로 사용되는 인터페이스의 모음
- 객체(Entity)를 통해 데이터 조작
- 복잡한 매핑 작업에 유리
- JPQL 사용
- 유지보수 쉬움, CRUD 자동화
- JPA를 구현한 구현체 : Hibernate, EclipseLink 등
- 내부적으로 JDBC를 기반으로 동작한다.

## SQL

테이블명, 컬럼명을 기준으로 작성

## JPQL

엔티티명, 필드명을 기준으로 작성

JPA에서 사용하는 객체지향 쿼리 언어

객체를 기준으로 하므로 DB 구조가 바뀌어도 유연함

## Repository 형식

public interface `Repository명` extends JpaRepository<`JPA로 사용할 entity`, `PK타입`>{}

## 쿼리 메소드

쿼리 메소드는 메소드의 이름을 분석하여 JPQL 쿼리를 실행

## @Query

실행할 메소드 위에 정적 쿼리를 작성할 때 사용

SQL이 아닌 JPQL 쿼리가 들어가야 함

# DTO(Data Transfer Object: 데이터 전송 객체)

- 계층 간 데이터 전송을 위해 도메인 모델 대신 사용되는 객체
- Request,Response에는 꼭 행위(Create, Update, Get)를 붙힌다.
- 파일 정렬등을 고려하면 도메인명을 앞으로 빼도 무방하다

관련 어노테이션

@Builder : Builder를 자동으로 생성해주는 어노테이션

@Data란?

다음의 어노테이션과 다양한 메소드를 자동 생성해줌

@Getter, @Setter, @ToString, @EqualsAndHashCode, @RequiredArgsConstructor

@Data를 지양해야 하는 이유

1. 불필요한 메소드 생성

   equals, hashCode 메소드 등 불필요한 메소드가 생성

2. 무분별한 Setter 남용

   Setter는 객체를 언제든지 변경할 수 있는 상태로 만듦

   객체의 안정성을 보장받기 어려움

3. ToString으로 인한 양방향 연관관계시 순환 참조 문제

프로젝트에서 어노테이션을 제한하는 방법

lombok.config 설정 파일을 통해 lombok 어노테이션 제한 가능

```
lombok.Setter.flagUsage = error
lombok.data.flagUsage = error
```

# Service

비즈니스 로직을 담당, 가장 복잡한 코드가 들어가는 부분

Controller에서는 Service 메소드를 호출,

Service에서는 Repository 메소드를 호출함

관련 어노테이션

@Service: 비즈니스 로직을 수행하는 서비스 레이어 클래스임을 나타냄

@Transactional : 선언적 데이터베이스 트랜잭션 관리 방법 제공

- public 메소드에만 적용됨
- 같은 객체 내 다른 메소드에서 호출 시, 트랜잭션 적용 X

  → Spring AOP 때문!

# 트랜잭션

데이터 거래에 있어서 안전성을 확보하기 위한 방법

데이터 처리 과정에서 오류 발생

→ 결과를 재반영하는 것이 아니라 모든 작업을 원 상태로 복구함 (Rollback)

처리 과정이 모두 성공

→ 결과를 반영함 (Commit)

## 트랜잭션의 특징 4가지

|     | 트랜잭션은 데이터베이스에 모두 반영되거나 모두 반영되지 않아야 한다.                  |
|-----|---------------------------------------------------------|
| 일관성 | 트랜잭션이 시작하기 전과 끝난 후에도 데이터베이스는 일관된 상태로 유지해야 한다.           |
| 독립성 | 여러 트랙잭션이 동시에 실행될 때, 각 트랜잭션은 다른 트랜잭션의 작업에 영향을 받지 않아야 한다. |
| 지속성 | 트랜잭션이 성공적으로 완료되었을 때 결과는 영구적으로 반영되어야 한다.                 |

## 트랜잭션 선언 위치

### 클래스 단에서 선언

- 해당 클래스의 모든 public 메소드에 적용

### 메소드 단에서 선언

- 해당 메소드에만 적용
- 구체적인 설정 가능
- 우선순위 ↑

# Converter란?

한 객체를 다른 객체로 바꾸는 역할을 하는 클래스나 메소드

DTO ↔ Entity 변환에 주로 사용

## Converter 생성 방법

1. Service 클래스 내부에 Converter 메소드 생성
2. Converter 패키지 생성 후 Converter 클래스 생성
3. Spring의 Converter<S,T> 인터페이스 사용

# ResponseEntity

개발자가 직접 결과 데이터와 HTTP 상태 코드를 제어할 수 있는 클래스

결과값, 상태코드, 헤더값을 모두 프론트에 넘겨줄 수 있고,

에러코드 또한 섬세하게 설정하여 넘길 수 있음

## HTTP 응답 상태 코드

1xx : 정보 전달

2xx : 성공

3xx : 리디렉션

4xx : 클라이언트 오류

5xx : 서버 오류

## ResponseEntity의 메소드들

- ResponseEntity.ok()
    - 200 OK 응답 반환
    - 요청이 정상적으로 처리되었음을 나타냄
    - 200 상태코드와 함께 body 반환 가능
- ResponseEntity.noContent() : 204 No Content 반환
- ResponseEntity.badRequest() : 400 Bad Request 응답 반환
- ResponseEntity.status() : 다른 HTTP 상태 코드 반환
- ResponseEntity.headers() : 헤더 설정 가능