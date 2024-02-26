package ru.klaes.library.api;

import lombok.Data;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.reactive.server.WebTestClient;
import ru.klaes.library.JUnitSpringBootBase;
import ru.klaes.library.model.Book;
import ru.klaes.library.repository.BookRepository;

class BookControllerTest extends JUnitSpringBootBase {

    @Autowired
    WebTestClient webTestClient;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Data
    static class JUnitBookResponse {
        private Long id;
        private String name;
    }

    @Test
    void getBookByIdSuccessTest() {
        Book testBook = new Book();
        testBook.setName("expectedBook");
        Book expectedBook = bookRepository.save(testBook);

        JUnitBookResponse responseBody =
                webTestClient.get()
                             .uri("/book/" + expectedBook.getId())
                             .exchange()
                             .expectStatus().isOk()
                             .expectBody(JUnitBookResponse.class)
                             .returnResult().getResponseBody();

        Assertions.assertNotNull(responseBody);
        Assertions.assertEquals(expectedBook.getId(), responseBody.getId());
        Assertions.assertEquals(expectedBook.getName(), responseBody.getName());
    }

    @Test
    void getBookByIdNotFoundTest() {
        Long maxId = jdbcTemplate.queryForObject("select max(id) from book", Long.class);
        if (maxId == null) {
            maxId = 1L;
        } else {
            maxId += 1L;
        }

        webTestClient.get()
                     .uri("/book/" + maxId)
                     .exchange()
                     .expectStatus().isNotFound();
    }

    @Test
    void addBookTest() {
        JUnitBookResponse testNewBook = new JUnitBookResponse();
        testNewBook.setName("newTestBook");

        JUnitBookResponse responseBody =
                webTestClient.post()
                             .uri("/book")
                             .bodyValue(testNewBook)
                             .exchange()
                             .expectStatus().isCreated()
                             .expectBody(JUnitBookResponse.class)
                             .returnResult().getResponseBody();

        Assertions.assertNotNull(responseBody);
        Assertions.assertNotNull(responseBody.getId());
        Assertions.assertEquals(responseBody.getName(), testNewBook.getName());
        Assertions.assertTrue(bookRepository.findById(responseBody.getId()).isPresent());
    }

    @Test
    void removeBookByIdSuccessTest() {
        Book testBook = new Book();
        testBook.setName("testBook");
        Book bookForRemove = bookRepository.save(testBook);


        webTestClient.delete()
                     .uri("/book/" + bookForRemove.getId())
                     .exchange()
                     .expectStatus().isNoContent();
    }

    @Test
    void removeBookByIdNotFoundTest() {
        Long maxId = jdbcTemplate.queryForObject("select max(id) from book", Long.class);
        if (maxId == null) {
            maxId = 1L;
        } else {
            maxId += 1L;
        }

        webTestClient.delete()
                     .uri("/book/" + maxId)
                     .exchange()
                     .expectStatus().isNotFound();
    }
}