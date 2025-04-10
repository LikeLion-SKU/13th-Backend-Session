# 3주차 복습

다중 선택: 매주 세션

<aside>
📢

### API 명세서

API가 어떻게 동작하는지 자세히 설명한 문서

### API 명세서의 장점

- 명확한 소통
- 일관성 유지
- 효율적인 유지 보수

### API 명세서가 포함해야할 내용

- API 기본 정보 : 기본 URL, API 이름, API 버전..
- 앤드포인트 : API를 호출할 수 있는 URL
- HTTP 메서드 : GET, POST, PUT, PATCH, DELETE 등 어떤 동작을 할것인지
- API 인증 방식 : API키, JWT(JSON Web Token), OAuth2(비밀번호 없이도 3자가 접근할 수 있도록 도와주는 프레임워크) 등
- 요청 파라미터 : API 요청 시 필요한 값들

    <aside>
    ✅

  쿼리 파라미터 : URL의 ? 뒤에 오는 키-값 쌍

  경로 파라미터 : URL 경로 일부에 포함되는 값

  헤더 파라미터 : 메타정보 전달

  본문 파라미터 : 특정 형식으로 데이터 전송

    </aside>

- 응답 형식 : API가 반환하는 데이터의 형태로 JSON, XML 등 어떤것인지

    <aside>
    ✅

  JSON : 자바스크립트에서 객체를 표현하는 방식
    
  Jackson 라이브러리
  : 직렬화로 Java → JSON, 역직렬화로 JSON → Java 로 변환하는 라이브러리

    </aside>

- 상태 코드 : 404, 200, 400 등 요청 결과를 나타내는 숫자 코드
- 오류메시지 형식 : 오류 발생 시 반환되는 JSON메시지 형식과 예시 작성

*API 문서화 도구에는 POSTMAN, swagger 등이 있다*

</aside>

<aside>
📢

### API 문서화 도구 - swagger

```json
dependencies {
  ...
  // Swagger openapi-ui
  implementation
  'org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.1'
  implementation
  'org.springdoc:springdoc-openapi-starter-webmvc-api:2.8.1'
}
```

- 의존성 추가하기

```java
package com.likelion.springpractice.week03;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration // Swagger 설정을 담당, 스프링이 시작할때 자동으로 읽힘
public class SwaggerConfig {

  // server.servlet.context-path: 스프링 기본 URL을 의미
  // server.servlet.context-path:/api -> http://localhost:8090/api가 기본 URL
  @Value("${server.servlet.context-path:}")
  private String contextPath; //즉, 기본 URL을 가져와서 swagger서버 URL에 반영하기 위함

  @Bean
  public OpenAPI customOpenAPI() {
    Server localServer = new Server();
    localServer.setUrl(contextPath); //contextPath를 Swagger에 등록된 서버 URL로 설정
    localServer.setDescription("Local Server"); // swagger UI에 표시되도록 서버 설명 추가

    return new OpenAPI() // Swagger 문서를 구성할 OpenAPI 객체를 반환
        .addServersItem(localServer) // 위에서 설정한 서버 추
        .addSecurityItem(new SecurityRequirement().addList("bearerAuth")) // 인증방식을 bearer로 하겠다고 설정
        .components( // JWT 토큰 사용하기 위한 코드
            new Components()
                .addSecuritySchemes(
                    "bearerAuth",
                    new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT") // Bearer방식으로 JWT 토큰을 사용하겠다고 설정
                )
        )
        // swagger 문서에 표시될 제목, 버전, 설명을 설정
        .info(new Info().title("Swagger API 명세서").version("1.0").description("MySwagger"));

  }

  // Swagger API 문서를 그룹으로 나눌 수 있도록 설정하는 메소드
  @Bean
  public GroupedOpenApi customGroupedOpenApi() {
    //api라는 이름의 그룹을 만들고 /**으로 모든 경로의 API를 이 그룹에 포함시킨다
    return GroupedOpenApi.builder().group("api").pathsToMatch("/**").build();
  }
}

```

Config 작성 : 환경설정을 위해 필요한 클래스 작성

- contextPath는 기본으로 되어있기에 http://localhost:8090
- contextPath값을 가져와 만든 swagger URL은  
  http://localhost:8090/swagger-ui/index.html

</aside>

<aside>
📢

### http 메소드

```java
package com.likelion.springpractice.week03;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // 이 클래스를 컨트롤러 클래스로 설정하고 JSON이나 문자열을 반환
@RequestMapping("/api") //  기본 URL/api를 이 컨트롤러 클래스와 매핑
public class HttpMethodController {

  @PostMapping("/create-test") // POST 요청이 들어올때 /api/create-test을 실행
  public String createTest() {
    return "This is create test";
  }

  @GetMapping("read-test") // GET 요청이 들어올때 /api/read-test을 실행
  public String readtest() {
    return "This is read test";
  }

  @PatchMapping("update-test") // PATCH 요청이 들어올때 /api/update-test을 실행
  public String updatePatchTest() {
    return "This is update patch test";
  }

  @PutMapping("update-test") // PUT 요청이 들어올때 /api/update-test을 실행
  public String updatePutTest() {
    return "This is update put test";
  }

  @DeleteMapping("delete-test") // DELETE 요청이 들어올때 /api/delete-test을 실행
  public String deleteTest() {
    return "This is delete test";
  }
}

```

컨트롤러 클래스는 사용자의 요청을 받아서 처리하고 그에 맞는 응답을 하는 클래스

### PATCH와 PUT의 차이

PUT : 데이터 전체수정으로 전체 데이터를 아예 다른 데이터로 덮어 씌워버린다
→ 즉, 같은 요청을 보내도 계속 덮어씌운다 → 멱등성을 가짐

PATCH : 데이터 부분 수정으로 , 일부값을 누적하거나 조건적으로 수정하기 때문에 요청을 여러번 보내면 결과가 달라져서 멱등하지 않다

</aside>

<aside>
📢

### 다양한 애너테이션

@Getter : Getter 메소드 자동 생성

@Setter : Setter 메소드 자동 생성

@Builder : 객체 생성시 여러 인자들을 명시적으로 전달할 수 있게 해준다

```java
import lombok.Builder;

@Builder
public class User {

  private String username;
  private String email;
  private String password;
}

User user = User.builder() //이런식으로 명시적으로 메소드에 인자 전달 가능
    .username("gpt")
    .email("gpt@example.com")
    .password("1234")
    .build();
```

@AllArgsConstructor : 클래스에 선언된 모든 필드를 인자로 받는 생성자 자동 생성 기능

@NoArgsConstructor : 매개변수 없는 기본 생성자 자동으로 생성해주는 기능

@RequiredArgsConstructor : 필수적인 필드들만 매개변수로 가지는 생성자를 자동으로 생성해주는 기능

@Configuration : 설정 클래스임을 표현

```java

@Configuration //설정 클래스임을 나타낸다
public class SwaggerConfig {
...
}
```

@Bean : Spring Container에서 관리하는 객체인 빈으로 등록할때 사용

@Value : 외부 프로퍼티 값이나 환경변수를 Spring bean 필드에 주입할때 사용

→ 즉, 매개변수가 있는 생성자와 getter가 있는 효과

```java

@Value
public class User {

  String name;
  String email;
}
-------------------------

// 이것과 같은 효과
public final class User {

  private final String name;
  private final String email;

  // 생성자
  public User(String name, String email) {
    this.name = name;
    this.email = email;
  }

  // getter만 있음
  public String getㅜame() {
    return name;
  }
}
```

@Data : 매개변수가 없는 생성자와 Getter, Setter 함수를 생성해주는 효과

```java

@Data
public class User {

  private String name;
  private String email;
}
-------------------------------------

public class User {

  private String name;
  private String email;

  // getter
  public String getName() {
    return name;
  }

  // setter
  public void setName(String name) {
    this.name = name;
  }
  // toString, equals, hashCode 등 자동 생성
}
```

</aside>