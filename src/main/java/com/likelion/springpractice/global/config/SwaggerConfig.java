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

@Configuration
public class SwaggerConfig {
  @Value("${server.servlet.context.path:}")
  //공개되면 안 되는 키 등. 환성변수가 없을 때 콜론 뒤에 걸 쓸 거다. 안 써있으면 8080으로 알아서 맞춰줌
  private String contextPath;

  @Bean
  public OpenAPI customOpenAPI(){
    Server localServer = new Server();
    localServer.setUrl(contextPath);
    localServer.setDescription("Local Server");

    return new OpenAPI()
        .addServersItem(localServer)
        .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
        .components(
            new Components()
                .addSecuritySchemes(
                    "bearerAuth",
                    new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")))
        .info(new Info().title("Swagger API 명세서").version("1.0").description("My Swagger"));
  }

  @Bean
  public GroupedOpenApi customGroupedOpenApi(){
    return GroupedOpenApi.builder().group("api").pathsToMatch("/**").build();
  }
}
