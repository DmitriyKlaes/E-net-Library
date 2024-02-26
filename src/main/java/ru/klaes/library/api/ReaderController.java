package ru.klaes.library.api;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.klaes.library.api.annotation.swagger_description.reader.SwaggerReaderDeleteDescription;
import ru.klaes.library.api.annotation.swagger_description.reader.SwaggerReaderGetDescription;
import ru.klaes.library.api.annotation.swagger_description.reader.SwaggerReaderIssuesGetDescription;
import ru.klaes.library.api.annotation.swagger_description.reader.SwaggerReaderPostDescription;
import ru.klaes.library.model.Issue;
import ru.klaes.library.model.Reader;
import ru.klaes.library.service.ReaderService;

import java.util.List;

@RestController
@RequestMapping("/reader")
@RequiredArgsConstructor
@Tag(name = "Читатели", description = "Контроллер для управления читателями")
public class ReaderController {

    private final ReaderService readerService;

    @SwaggerReaderGetDescription(summary = "Получить читателя",
                                 description = "Получить читателя по идентификатору 'ID'.")
    @GetMapping("/{id}")
    public ResponseEntity<Reader> getReader(@PathVariable @Parameter(description = "'ID' читателя") Long id) {
        Reader queryReader = readerService.getReaderById(id);
        if (queryReader == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(queryReader);
    }

    @SwaggerReaderIssuesGetDescription(summary = "Получить список выдачей читателя",
                                       description = "Получает список выдачей читателя по идентификатору 'ID'.")
    @GetMapping("/{id}/issue")
    public ResponseEntity<List<Issue>> getReaderIssues(@PathVariable @Parameter(description = "'ID' читателя") Long id) {
        List<Issue> queryIssue = readerService.getIssueByReaderId(id);
        if (queryIssue == null) {
            return ResponseEntity.notFound().build();
        }
        if (queryIssue.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(queryIssue);
    }

    @SwaggerReaderPostDescription(summary = "Добавить читателя",
                                  description = "Добавить читателя в базу данных.")
    @PostMapping
    public ResponseEntity<Reader> addReader(@RequestBody Reader reader) {
        readerService.addReader(reader);
        return ResponseEntity.status(HttpStatus.CREATED).body(reader);
    }

    @SwaggerReaderDeleteDescription(summary = "Удалить читателя",
                                    description = "Удаляет читателя из базы данных.")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> removeReader(@PathVariable @Parameter(description = "'ID' читателя") Long id) {
        Reader queryReader = readerService.getReaderById(id);
        if (queryReader == null) {
            return ResponseEntity.notFound().build();
        }
        readerService.removeReaderById(id);
        return ResponseEntity.noContent().build();
    }
}
