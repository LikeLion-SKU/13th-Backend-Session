# # Spring Boot (Week 5) -DTO, Service, Repository

---

## 1. 아키텍처 구조

- **Client → Controller → Service → Repository → Database**
- DTO: 데이터 전송을 위한 객체
- Service: 비즈니스 로직 처리
- Repository: JPA를 통해 DB 접근

---

## 2. ORM과 JPA

### 🔸 ORM (Object Relational Mapping)
- 객체와 데이터베이스 테이블을 자동 매핑
- SQL 작성 없이 객체로 DB와 상호작용 가능

### 🔸 JPA (Java Persistence API)
- 자바 ORM 기술 표준
- Hibernate, EclipseLink 등이 구현체
- CRUD 자동화, 유지보수 용이

---

## 3. JDBC vs JPA

| 항목       | JDBC                                  | JPA                                      |
|------------|---------------------------------------|-------------------------------------------|
| SQL        | 직접 작성 필요                        | 자동 처리 가능                            |
| 유지보수   | 어렵고 코드 반복                      | 유지보수 쉬움, 코드 간결                  |
| 장점       | SQL 제어 유리                         | 객체 중심 설계, 생산성 향상               |

---

## 4. Spring vs Spring Boot

| 항목           | Spring                              | Spring Boot                           |
|----------------|--------------------------------------|----------------------------------------|
| 설정           | 복잡, 수동                           | 간단, 자동                             |
| 서버           | 별도 설정 (war 파일)                | 내장 서버 제공 (jar 파일)              |
| 의존성 관리    | 수동                                 | 자동                                   |

---

## 5. JPQL (Java Persistence Query Language)

- JPA에서 사용하는 객체지향 쿼리 언어
- SQL과 달리 엔티티 이름, 필드명을 기준으로 작성

---

## 6. Repository

- `JpaRepository<T, ID>` 상속
- 기본 CRUD 자동 제공
- **쿼리 메서드**: 메서드명으로 자동 JPQL 생성
- **@Query** 어노테이션으로 직접 JPQL 작성 가능

---

## 7. DTO (Data Transfer Object)

- 계층 간 데이터 전달 역할
- 도메인 모델을 직접 전달하지 않기 위해 사용
- **Request/Response DTO 명명법**: `CreatePostRequest`, `PostResponse` 등

### 🔸 Lombok 관련
- `@Builder`: 객체 생성 편의 제공
- `@Data` 지양:
    - 불필요한 Setter, toString 등 자동 생성
    - 순환참조 문제 및 객체 안정성 저해

---

## 8. Service

- 비즈니스 로직 담당
- Controller → Service → Repository 구조
- `@Service`: 서비스 레이어 클래스 표시
- `@Transactional`: 트랜잭션 보장

---

## 9. 트랜잭션 (Transaction)

### 🔸 특징 (ACID)
1. **원자성 (Atomicity)**: 모두 성공 또는 모두 실패
2. **일관성 (Consistency)**: 데이터의 유효성 유지
3. **격리성 (Isolation)**: 동시성 문제 방지
4. **지속성 (Durability)**: 성공 시 영구 반영

---

## 10. Converter

- DTO ↔ Entity 변환기
- 내부 메소드 또는 별도 `Converter` 클래스/패키지로 분리

---

## 11. Controller

- 클라이언트 요청을 받아 Service 호출
- `ResponseEntity` 사용:
    - HTTP 상태 코드 + body 데이터 함께 반환
    - `.ok()`, `.notFound()`, `.badRequest()` 등 다양한 응답 형태

---