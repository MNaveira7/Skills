package com.fct.clubs.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Swagger Proyecto FCT")
                        .version("1.0")
                        .description("Endpoints organizados por tags, necesarios para la funcionalidad del backend"));
    }

}
