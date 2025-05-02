## JPA

- 자바에서 ORM 기술의 표준으로 사용되는 인터페이스의 모음
- JPA를 구현한 구현체 : Hibernate, EclipseLink

## ORM

- Object Relational Mapping
- 관계형 데이터베이스의 객체를 자동으로 연결해주는 기술
- CRUD SQL 쿼리문을 작성하지 않아도 DB와 상호 작용 하도록 해줌

`@Repositorypublic interface PostRepository extends JpaRepository<Post, Long> {  List<Post> findAllByOrderByCreatedAtDesc(); // 게시글 생성 시간을 기준으로 내림차순(최신순)으로 조회  List<Post> findAllByOrderByViewsDesc(); // 게시글을 조회수가 많은 순으로 조회}`

→ 이처럼 사용자 정의 쿼리를 작성할 때 쿼리문을 작성하지 않아도 DB와 상호작용 하도록 해준다 (메소드 이름으로 쿼리를 생성하는 기능을 제공함)

| JDBC | JPA |
| --- | --- |
| 데이터베이스와 통신하기 위한 API |  OMR 기술의 표준으로 사용되는 인터페이스 |
| 개발자가 SQL 쿼리문을 직접 작성한다. | 객체(엔티티)를 통해 데이터 조작 |
| 성능 최적화, SQL제어 유리 | 복잡한 매핑 작업에 유리, JPQL 사용 |
| 코드 반복, 유지보수 떨어짐 | 유지보수가 쉽고, CRUD 자동화 |

→ JDBC와 JPA는 아예 다르지 않다.

## DTO

- DAta Transfer Object, 데이터 전송 객체
- 계층 간 데이터 전송을 위해 도메인 모델 대신 사용되는 객체
- @Data 어노테이션 사용을 지양해야 한다
    - 불필요한 메소드 생성
        - equals, hashCode . . .
    - 무분별한 Setter 남용
        - Setter는 객체를 수정할 수 있음
        - 객체 안정성 보장이 안됨
    - ToString으로 인한 양방향 연관 관계시 순환 참조 문제

## Service

- 비즈니스 로직 담당
- Controller는 Service를 호출하고 Service는 Repository를 호출한다.
    - API만들 때 Reposutory, Service, Controller순으로 개발하니까 편했음
- @Transaction
    - public 메소드에만 적용
    - 같은 객체 내 다른 메소드에서 호출 시, 트랜잭션 적용x

### Spring에서 @Transaction

- 메서드가 실행되는 동안 트랜잭션을 시작하고, 예외가 발생하면 자동으로 롤백해준다.
    - 트랜잭션은 모두 성공하거나 모두 실패해야함 (원자성)
        - 따라서 데이터 처리 과정에서 오류가 발생하면 결과를 재반영 하지 않고 모든 작업을 원상태로 복구함 (Rollback)
- 같은 클래스 내에서 메서드를 직접 호출하면 @Transaction이 무시됨

    ```
    @Transactional
    public void outerMethod() {
        innerMethod();  // 이렇게 직접 호출하면 트랜잭션이 적용되지 않음
    }
    
    @Transactional
    public void innerMethod() {
        // 트랜잭션이 필요한 작업
    }
    ```


→ outerMethod() 에서 innerMethod() 를 직접 호출하였기 떄문에 innerMethod()에 붙은 @Transaction은 적용되지 않고 무시됨

- spring에서 @Transaction은 AOP으로 구현 되어있기 때문에 트랜잭션을 적용하기 위해 프록시 객체를 만들어서 감싸준다.
    - 하지만 자기 자신(this) 내부에서 메서드를 호출하면 프록시를 거치지 않고 실제 메서드를 바로 실행하기 때문에 @Transacton이 동작하지 않는다.

## Converter

- 한 객체를 다른 객체로 바꾸는 역할을 하는 클래스나 메소드
    - DTO ↔ Entity 변환에 주로 사용
- converter 생성 방법
    1. Service 클래스 내부에 Converter 메소드 생성
    2. Converter 패키지 생성 후 Converter 클래스 생성
    3. Spring의 Converter<S,T> 인터페이스 사용

## ResponseEntity

- 개발자가 직접 결과 데이터와 HTTP 상태 코드를 제어할 수 있는 클래스
- 결과 값, 상태 코드, 헤더 값을 모두 프론트에 넘겨줄 수 있고 에러코드 또한 섬세하게 설정하여 넘길 수 있음
    - HttpEntity(상태코드)를 상속 받음

`// 5주차 기능 추가 (게시글 조회수 순으로 조회)@Operation(summary = "게시글을 조회수 순으로 조회",    description = "게시판 페이지에서 조회수순 버튼을 눌렀을 때 게시글을 최신순으로 조회하는 API")@GetMapping("/posts/views")public ResponseEntity<List<PostResponse>> getViewsPosts() {  return ResponseEntity.*ok*(postService.getAllPostsByViewsDesc());}`

→ 반환 타입을 ReponseEntity로 감싸주었음

→ return에 ok() 메소드 사용

- ResponseEntity.ok()
    - 200 응답 반환 (정상 처리)
    - body 반환 가능

`public static <T> ResponseEntity<T> ok(@Nullable T body) {  return *ok*().<T>body(body);}`

- ResponseEntity.noyFound()
    - 404 응답 반환 (리소스 요청 값 x)
    - body 전송 x

`public static <T> ResponseEntity<T> of(Optional<T> body) {  Assert.*notNull*(body, "Body must not be null");  return (ResponseEntity)body.map(ResponseEntity::*ok*).orElseGet(() -> *notFound*().build());}`

`public static HeadersBuilder<?> notFound() {  return *status*(HttpStatus.*NOT_FOUND*);}` → 404

- ***HttpStatus enum 클래스에 가면 상태코드를 정의해둠***