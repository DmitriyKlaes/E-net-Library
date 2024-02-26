package ru.klaes.library.api.annotation.swagger_description.issue;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.core.annotation.AliasFor;
import ru.klaes.library.model.Issue;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Operation
@ApiResponses(value = {
        @ApiResponse(responseCode = "201",
                     description = "Книга успешно выдана",
                     content = {@Content(mediaType = "application/json",
                                         schema = @Schema(description = "Успешный ответ сервера",
                                                          implementation = Issue.class))}),
        @ApiResponse(responseCode = "404",
                     description = "Книга или читатель отсутствуют в базе данных",
                     content = @Content),
        @ApiResponse(responseCode = "409",
                     description = "Читатель больше не может брать книги (по умолчанию всего 3 открытых выдачи)",
                     content = @Content)
})
public @interface SwaggerIssuePostDescription {

    @AliasFor(annotation = Operation.class, attribute = "summary")
    String summary();

    @AliasFor(annotation = Operation.class, attribute = "description")
    String description();

}
