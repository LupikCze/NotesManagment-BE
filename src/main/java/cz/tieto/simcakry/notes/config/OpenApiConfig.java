package cz.tieto.simcakry.notes.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(info = @Info(
        title = "Notes API",
        description = "Notes API for notes management",
        version = "v1.0.0"
))
@SecurityScheme(
        name = "token",
        type = SecuritySchemeType.HTTP,
        in = SecuritySchemeIn.HEADER,
        scheme = "bearer",
        paramName = "Authorization",
        description = "Bearer {token}",
        bearerFormat = "JWT"
)


@Configuration
public class OpenApiConfig {
}
