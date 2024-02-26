package ru.klaes.library.api.annotation.swagger_description.book;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.core.annotation.AliasFor;
import ru.klaes.library.model.Book;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Operation
@ApiResponses(value = {
        @ApiResponse(responseCode = "200",
                     description = "Книга найдена",
                     content = {@Content(mediaType = "application/json",
                                         schema = @Schema(description = "Успешный ответ сервера",
                                                          implementation = Book.class))}),
        @ApiResponse(responseCode = "404",
                     description = "Книга отсутствует в базе данных",
                     content = @Content)
})
public @interface SwaggerBookGetDescription {

    @AliasFor(annotation = Operation.class, attribute = "summary")
    String summary();

    @AliasFor(annotation = Operation.class, attribute = "description")
    String description();

}
