package ru.klaes.library.api.annotation.swagger_description.reader;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.core.annotation.AliasFor;
import ru.klaes.library.model.Reader;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Operation
@ApiResponses(value = {
        @ApiResponse(responseCode = "201",
                     description = "Читатель добавлен",
                     content = {@Content(mediaType = "application/json",
                                         schema = @Schema(description = "Успешный ответ сервера",
                                                          implementation = Reader.class))})
})
public @interface SwaggerReaderPostDescription {

    @AliasFor(annotation = Operation.class, attribute = "summary")
    String summary();

    @AliasFor(annotation = Operation.class, attribute = "description")
    String description();

}
