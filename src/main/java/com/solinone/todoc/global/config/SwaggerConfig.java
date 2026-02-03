package com.solinone.todoc.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {
    @Value("${server.url}")
    private String serverUrl;

    @Value("${domain.url}")
    private String domainUrl;

    @Bean
    public OpenAPI openAPI() {
        String securitySchemeName = "BearerAuth";
        return new OpenAPI()
                .info(apiInfo())
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName, new SecurityScheme()
                                .name(securitySchemeName)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .description("JWT토큰을 입력, 'Bearer ' 접두사는 자동 추가")))
                .servers(servers());
    }

    private Info apiInfo() {
        return new Info()
                .title("ToDoc")
                .description("ToDoc API Documentation")
                .version("1.0.0");
    }

    private List<Server> servers() {
        return List.of(
                new Server()
                        .url(serverUrl)
                        .description("로컬 개발 서버"),
                new Server()
                        .url(domainUrl)
                        .description("배포 서버")
        );
    }
}
