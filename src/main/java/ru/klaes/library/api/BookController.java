package ru.klaes.library.api;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.klaes.library.api.annotation.swagger_description.book.SwaggerBookDeleteDescription;
import ru.klaes.library.api.annotation.swagger_description.book.SwaggerBookGetDescription;
import ru.klaes.library.api.annotation.swagger_description.book.SwaggerBookPostDescription;
import ru.klaes.library.model.Book;
import ru.klaes.library.service.BookService;

@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
@Tag(name = "Книги", description = "Контроллер для управления книгами")
public class BookController {

    private final BookService bookService;

    @SwaggerBookGetDescription(summary = "Получить книгу по ID",
                               description = "Получить книгу по идентификатору 'ID'.")
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBook(@PathVariable @Parameter(description = "'ID' книги") Long id) {
        Book queryBook = bookService.getBookById(id);
        if (queryBook == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(queryBook);
    }

    @SwaggerBookPostDescription(summary = "Добавить книгу",
                                description = "Добавить книгу в базу данных.")
    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        bookService.addBook(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(book);
    }

    @SwaggerBookDeleteDescription(summary = "Удалить книгу",
                                  description = "Удаляет книгу из базы данных.")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> removeBook(@PathVariable @Parameter(description = "'ID' книги") Long id) {
        Book queryBook = bookService.getBookById(id);
        if (queryBook == null) {
            return ResponseEntity.notFound().build();
        }
        bookService.removeBookById(id);
        return ResponseEntity.noContent().build();
    }
}
