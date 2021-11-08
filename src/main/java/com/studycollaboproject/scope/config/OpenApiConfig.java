package com.studycollaboproject.scope.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class OpenApiConfig {
    @Bean
    public OpenAPI springShopOpenAPI() {
        Info info = new Info()
                .title("Scope")
                .version("V1.0")
                .description("성향에 맞는 팀원을 추천받아 함께 사이드 프로젝트를 진행할 수 있는 서비스")
                .contact(new Contact()
                        .name("Web Site")
                        .url("http://kbumsoo.s3-website.ap-northeast-2.amazonaws.com/"))
                .license(new License()
                        .name("Apache License Version 2.0")
                        .url("http://www.apache.org/licenses/LICENSE-2.0"));

        SecurityScheme auth = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP).in(SecurityScheme.In.HEADER).scheme("bearer").bearerFormat("");
        SecurityRequirement securityRequirement = new SecurityRequirement().addList("authorization");

        return new OpenAPI()
                .components(new Components().addSecuritySchemes("authorization", auth))
                .addSecurityItem(securityRequirement)
                .info(info);
    }
}