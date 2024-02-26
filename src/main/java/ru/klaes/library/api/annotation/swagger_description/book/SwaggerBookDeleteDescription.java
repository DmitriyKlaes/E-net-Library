package ru.klaes.library.api.annotation.swagger_description.book;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Operation
@ApiResponses(value = {
        @ApiResponse(responseCode = "204",
                     description = "Книга удалена",
                     content = @Content),
        @ApiResponse(responseCode = "404",
                     description = "Книга отсутствует в базе данных",
                     content = @Content)
})
public @interface SwaggerBookDeleteDescription {

    @AliasFor(annotation = Operation.class, attribute = "summary")
    String summary();

    @AliasFor(annotation = Operation.class, attribute = "description")
    String description();

}
