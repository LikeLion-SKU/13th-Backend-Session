package com.likelion.springpractice.global.config;

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

@Configuration //configuration은, "환경 설정" !! 이 클래스가 스프링 설정 클래스임을 나타냄!!
public class SwaggerConfig {

  //application.yml 혹은 application.properties에서 server.servlet.context-path 값을 읽어온다.
  //예: server.servlet.context-path=/api 이면 contextPath = "/api"
  @Value("${server.servlet.context-path:}")
  private String contextPath;

  //Swagger OpenAPI 객체를 생성해 Bean등록! 이 객체는 Swagger UI가 보여줄 정보들을 설정하는 데 쓰인다.
  @Bean
  public OpenAPI customOpenAPI() {
    Server localServer = new Server();
    localServer.setUrl(contextPath); //현재 서버의 base URL 설정
    localServer.setDescription("Local Server"); //Swagger UI 왼쪽 상단의 “Servers” 드롭다운에 표시됨.

    return new OpenAPI()
        .addServersItem(localServer) //서버 목록에 localServer추가.
        .addSecurityItem(
            new SecurityRequirement().addList("bearerAuth")) //보안 요구 사항 설정 (bearerAuth를 사용하는 엔드포인트들)
        .components(
            new Components()
                .addSecuritySchemes( //보안 스키마 정의
                    "bearerAuth",
                    new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer") //bearer방식 (토큰을 헤더에 넣는 방식)
                        .bearerFormat("JWT"))) //JWT 형식의 토큰 사용 명시
        .info(new Info().title("Swagger API 명세서").version("1.0").description("My Swagger"));
    //api 문서 정보 설정!! 스웨거 UI에 제목, 버전, 설명 입력해놓은 것들이 띄워짐!!
  }

  @Bean
  public GroupedOpenApi customGroupedOpenApi() {
    return GroupedOpenApi.builder().group("api").pathsToMatch("/**").build(); //이 그룹의 이름 api!!
  }

  @Bean
  public GroupedOpenApi customGroupedOpenApitwo() {
    return GroupedOpenApi.builder().group("api-todo").pathsToMatch("api/todo/**")
        .build(); //이 그룹의 이름 api!!
    //**은, api로 시작하는 뒤의 모든것들에 대해서 불러오겠다!!
    //*는, api로 시작하는 뒤에 하나만 불러오겠다!!
    //이러면, update와 관련된 하위에 대해서만 불러온다!!
  }

  @Bean
  public GroupedOpenApi customGroupedOpenApiPosts() {
    return GroupedOpenApi.builder().group("api-posts").pathsToMatch("/api/v1/posts/**").build();
  }

}
