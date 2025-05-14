# Spring Boot (Week 3)

## 다양한 어노테이션

| 어노테이션 | 설명                                                               |
|------------|------------------------------------------------------------------|
| `@RestController` | Spring에서 REST API를 만들 때 사용 `@Controller + @ResponseBody`를 합친 것   |
| `@RequestMapping` | 요청 URL과 HTTP 메서드를 매핑할 때 사용 클래스나 메서드에 붙일 수 있음                     |
| `@GetMapping`, `@PostMapping`, `@PutMapping`, `@DeleteMapping`, `@PatchMapping` | 각각 GET, POST, PUT, DELETE, PATCH 요청을 처리할 메서드를 지정할 때 사용           |
| `@RequestParam` | 요청 파라미터 (query string, ?name=value 형태)를 메서드 인자로 받을 때 사용          |
| `@PathVariable` | URL 경로에 포함된 값을 변수로 받을 때 사용 (ex: `/user/{id}` 형태)                  |
| `@RequestBody` | 클라이언트가 전송한 JSON 데이터를 객체로 변환하여 받을 때 사용                            |
| `@ResponseBody` | 반환 데이터를 JSON이나 XML 형태로 응답 본문에 포함시킬 때 사용                          |
| `@Autowired` | 의존성 주입을 자동으로 수행할 때 사용                                            |
| `@Component`, `@Service`, `@Repository` | 각각 일반 Bean, 서비스 계층, DAO 계층을 Spring이 자동으로 감지해서 Bean으로 등록하게 할 때 사용 |
| `@Valid`, `@Validated` | 요청 데이터의 유효성 검사를 수행할 때 사용                                         |
| `@Entity` | JPA에서 DB 테이블과 매핑되는 클래스임을 명시할 때 사용                                |
| `@Id`, `@GeneratedValue` | Entity의 기본 키(PK)를 지정하고, 자동 생성 전략을 설정할 때 사용                       |

---

## API 명세서

API의 구조와 동작 방식을 문서화한 것으로 **서버와 클라이언트 간의 소통을 원활하게 하기 위한 핵심 문서**

### API 명세서의 목적
- **명확한 소통**: 개발자 간 의사소통 원활
- **일관성 유지**: 설계 및 구현의 일관성
- **효율적인 유지보수**: 수정 시 영향 파악 용이

---

## API 명세서 작성 시 포함할 내용

1. **기본 정보**
    - API 이름
    - 버전
    - 작성자 및 작성일

2. **인증 방식 명시**
    - ex: JWT 토큰, OAuth 2.0, API Key 등

3. **Endpoint + HTTP Method**
    - ex: `GET /api/users`, `POST /api/login`

4. **요청 파라미터**
    - 쿼리 파라미터(Query), 경로 변수(Path), 바디 데이터(Body)

5. **응답 구조**
    - JSON 형태의 응답 데이터
    - 성공 / 실패 구조 분리

6. **에러 코드 및 메시지**
    - ex: `400 Bad Request`, `401 Unauthorized`, `500 Internal Server Error`

---

## 예시 API 명세서

| 항목 | 내용 |
|------|------|
| URL | `POST /api/user/login` |
| 설명 | 로그인 처리 |
| 요청 | `{"email": "test@example.com", "password": "1234"}` |
| 응답 | `{"token": "eyJhbGci...","nickname": "홍길동"}` |
| 인증 | JWT 토큰 필요 없음 (로그인 전이므로) |
| 에러 | `401 Unauthorized` - 잘못된 로그인 정보 |

---

## JSON & Jackson

### JSON (JavaScript Object Notation)
- 데이터 교환을 위한 포맷
- `{ "key": "value" }` 구조
- 경량 & 언어 독립적

### Jackson
- Java에서 JSON 변환을 처리하는 대표적인 라이브러리
- 직렬화: 객체 → JSON
- 역직렬화: JSON → 객체

```java
// ex: JSON → 객체
ObjectMapper mapper = new ObjectMapper();
User user = mapper.readValue(jsonString, User.class);
```

---

## CRUD와 HTTP Method

| 기능 | HTTP 메서드 | 설명 |
|------|--------------|------|
| Create | POST | 새로운 데이터 생성 |
| Read | GET | 데이터 조회 |
| Update | PUT / PATCH | 기존 데이터 수정<br>PUT: 전체 수정<br>PATCH: 부분 수정 |
| Delete | DELETE | 데이터 삭제 |


---
### PUT vs PATCH

| 구분 | PUT | PATCH |
|------|-----|-------|
| 정의 | 리소스를 전체 교체 (Complete Replace) | 리소스를 부분 수정 (Partial Update) |
| 요청 Body | 전체 리소스가 포함되어야 함 | 변경할 데이터만 포함 가능 |
| 멱등성 | 있음 (같은 요청 여러 번 보내도 결과 동일) | 없음 (동일 요청 여러 번 보내면 결과 달라질 수 있음) |
| 사용 예 | 사용자 정보 전체 변경 | 사용자 닉네임만 변경 등 일부 필드 수정 |

---

## 🗑Soft Delete vs Hard Delete

| 구분 | Soft Delete | Hard Delete |
|------|-------------|-------------|
| 정의 | 데이터를 실제로 삭제하지 않고, 접근을 제한하여 사용자에게 보이지 않게 처리 | 데이터를 데이터베이스에서 완전히 삭제 |
| 장점 | 복구 가능, 삭제 이력 관리 용이 | 불필요한 데이터 제거로 저장공간 확보 |
| 구현 방식 | `is_deleted` 컬럼 (boolean) 사용하여 `true/false`로 구분 | `DELETE` SQL 명령으로 완전 삭제 |
| 예시 | `is_deleted = 0`이면 조회 가능, `1`이면 조회 불가 | DB에서 해당 row 완전 제거 |
