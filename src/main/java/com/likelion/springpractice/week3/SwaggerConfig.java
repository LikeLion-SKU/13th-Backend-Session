package com.likelion.springpractice.week3;

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

@Configuration
public class SwaggerConfig {

  // @Value 붙으면 따로 관리해주는거 ip를 공개하면 위험해서
  @Value("${server.servlet.context-path:}")// 넣으면 이걸로..
  private String contextPath;

  @Bean
  public OpenAPI customOpenAPI() {
    Server localServer = new Server(); // 서버를 만든거...?
    localServer.setUrl(contextPath);
    localServer.setDescription("Local Server");

    return new OpenAPI()
        .addServersItem(localServer)
        .addSecurityItem(new SecurityRequirement().addList("bearerAuth")) // jws 토큰을 사용하기 위해서 베어럴어쓰? 그걸 넣을 칸을 만들어 준다는 뜻.
        .components(
            new Components()
                .addSecuritySchemes(
                    "bearerAuth",
                    new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")))
        .info(new Info().title("Swagger API 명세서").version("1.0").description("My Swagger")); // 명세서 기본 네이밍
  }
  @Bean
  public GroupedOpenApi customGroupedOpenApi() {
    return GroupedOpenApi.builder().group("api").pathsToMatch("/**").build();
  }
}
