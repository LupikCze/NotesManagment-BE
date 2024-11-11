package cz.tieto.simcakry.notes.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(info = @Info(
        title = "Notes API",
        description = "Notes API for notes management",
        version = "v1.0.0"
))

@Configuration
public class OpenApiConfig {
}
