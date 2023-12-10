package ru.vanek.task_management_application.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Ivan",
                        email = "ivshvl39@gmail.com"
                ),
                description = "OpenApi документация для Системы Управления Задачами",
                title = "OpenApi спецификация - Ivan"
        ),
        servers = @Server(
                description = "Локальный сервевр",
                url = "http://localhost:8088/test"
))
@SecurityScheme(
        name = "bearerAuth",
        description = "Авторизация при помощи JWT токена",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}
