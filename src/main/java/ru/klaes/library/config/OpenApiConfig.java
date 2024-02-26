package ru.klaes.library.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(title = "Библиотека Api",
                     description = "Домашняя работа Spring Boot", version = "1.0.0",
                     contact = @Contact(name = "Пономаренко Дмитрий",
                                        email = "89119584059@mail.ru",
                                        url = "https://vk.com/id824770"
                )
        )
)
public class OpenApiConfig {
}
