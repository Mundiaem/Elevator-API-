package com.building.elevator.config;

import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.tags.Tag;
import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

import java.util.Collections;
import java.util.Comparator;
import java.util.stream.Collectors;

/**
 * Configuration of swagger.
 * Swagger config will be used both for exporting Swagger UI and for OpenAPI specification generation.
 */
@Configuration
public class SwaggerConfig {

    /**
     * Elevator API
     */
    @Bean
    public GroupedOpenApi userApi() {
        final String[] packagesToScan = {"com.building.elevator.controller"};
        return GroupedOpenApi
                .builder()
                .group("Elevator API")
                .packagesToScan(packagesToScan)
                .pathsToMatch("/users/**")
                .addOpenApiCustomiser(bearerAuthCustomizer())
                .addOpenApiCustomiser(tagsSorterCustomizer())
                .addOpenApiCustomiser(userApiCustomizer())
                .build();
    }

    private OpenApiCustomiser userApiCustomizer() {
        return openAPI -> openAPI
                .info(new Info()
                        .title("Springboot & OpenAPI")
                        .description("Elevator API documentation with OPENAPI")
                        .version("3.0.0")
                        .contact(new Contact()
                                .name("Dominic Mundia")
                                .url("")
                                .email("")));


    }

    /**
     * Defines Bearer authorization security scheme.
     */
    private OpenApiCustomiser bearerAuthCustomizer() {
        return openAPI -> openAPI
                .schemaRequirement("Bearer", new SecurityScheme()
                        .name("Authorization")
                        .description("JWT Authorization header using the Bearer scheme. Example: \\\\\\\"Authorization: Bearer {token}\\\\\\\"")
                        .type(SecurityScheme.Type.APIKEY)
                        .in(SecurityScheme.In.HEADER)
                )
                .security(Collections.singletonList(new SecurityRequirement().addList("Bearer")));
    }

    /**
     * Defines basic authorization security scheme.
     */
    private OpenApiCustomiser basicAuthCustomizer() {
        return openAPI -> openAPI
                .components(openAPI.getComponents()
                        .addSecuritySchemes("Basic",
                                new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("basic")));
    }

    /**
     * Adjusts API definition to follow the API validation rule requiring alphabetically sorted tags.
     */
    private OpenApiCustomiser tagsSorterCustomizer() {
        return openAPI -> openAPI
                .tags(openAPI.getTags().stream()
                        .sorted(Comparator.comparing(Tag::getName)).collect(Collectors.toList()));

    }


}