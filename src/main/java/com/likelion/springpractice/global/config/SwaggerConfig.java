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

    @Value("${server.servlet.context-path:}")
    private String contextPath; //Value 어노테이션을 통해서 환경변수 관리

    @Bean
    public OpenAPI customOpenAPI() {
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
    public GroupedOpenApi customGroupedOpenApiTest() {
        return GroupedOpenApi.builder().group("api-test").pathsToMatch("/api/*-test").build();
    }

    @Bean
    public GroupedOpenApi customGroupedOpenApiPosts() {
        return GroupedOpenApi.builder().group("api-posts").pathsToMatch("/api/posts/**").build();
    }

    @Bean
    public GroupedOpenApi customGroupedOpenApiUsers() {
        return GroupedOpenApi.builder().group("api-users").pathsToMatch("/api/users/**").build();
    }

    @Bean
    public GroupedOpenApi customGroupedOpenApiAuths() {
        return GroupedOpenApi.builder().group("api-auths").pathsToMatch("/api/auths/**").build();
    }

    @Bean
    public GroupedOpenApi customGroupedOpenApiBatches() {
        return GroupedOpenApi.builder().group("api-batches").pathsToMatch("/api/batches/**")
            .build();
    }

    @Bean
    public GroupedOpenApi customGroupedOpenApiFoods() {
        return GroupedOpenApi.builder().group("api-foods").pathsToMatch("/api/foods/**").build();
    }

    @Bean
    public GroupedOpenApi customGroupedOpenApiFoodLikes() {
        return GroupedOpenApi.builder().group("api-foodlikes").pathsToMatch("/api/foodlikes/**")
            .build();
    }

    @Bean
    public GroupedOpenApi customGroupedOpenApiFoodReviews() {
        return GroupedOpenApi.builder().group("api-foodreviews").pathsToMatch("/api/foodreviews/**")
            .build();
    }

    @Bean
    public GroupedOpenApi customGroupedOpenApiUserBatches() {
        return GroupedOpenApi.builder().group("api-userbatches").pathsToMatch("/api/userbatches/**")
            .build();
    }

}
