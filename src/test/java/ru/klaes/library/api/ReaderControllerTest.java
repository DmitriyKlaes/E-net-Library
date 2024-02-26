package ru.klaes.library.api;

import lombok.Data;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.reactive.server.WebTestClient;
import ru.klaes.library.JUnitSpringBootBase;
import ru.klaes.library.model.Book;
import ru.klaes.library.model.Issue;
import ru.klaes.library.model.Reader;
import ru.klaes.library.repository.BookRepository;
import ru.klaes.library.repository.IssueRepository;
import ru.klaes.library.repository.ReaderRepository;

import java.util.List;
import java.util.Objects;

class ReaderControllerTest extends JUnitSpringBootBase {

    @Autowired
    WebTestClient webTestClient;
    @Autowired
    ReaderRepository readerRepository;
    @Autowired
    IssueRepository issueRepository;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    JdbcTemplate jdbcTemplate;

    Book testBook;
    Reader testReader;

    @Data
    static class JUnitReaderResponse {
        private Long id;
        private String name;
        private List<Issue> listIssues;
    }

    @BeforeEach
    void setUp() {
        Book testBook = new Book();
        testBook.setName("testBook");
        this.testBook = bookRepository.save(testBook);
        Reader testReader = new Reader();
        testReader.setName("testReader");
        this.testReader = readerRepository.save(testReader);
    }

    @AfterEach
    void clear() {
        issueRepository.deleteAll();
    }

    @Test
    void getReaderByIdSuccessTest() {
        JUnitReaderResponse responseBody =
                webTestClient.get()
                             .uri("/reader/" + testReader.getId())
                             .exchange()
                             .expectStatus().isOk()
                             .expectBody(JUnitReaderResponse.class)
                             .returnResult().getResponseBody();

        Assertions.assertNotNull(responseBody);
        Assertions.assertEquals(testReader.getId(), responseBody.getId());
        Assertions.assertEquals(testReader.getName(), responseBody.getName());
    }

    @Test
    void getReaderByIdNotFoundTest() {
        Long maxId = jdbcTemplate.queryForObject("select max(id) from reader", Long.class);
        if (maxId == null) {
            maxId = 1L;
        } else {
            maxId += 1L;
        }

        webTestClient.get()
                     .uri("/reader/" + maxId)
                     .exchange()
                     .expectStatus().isNotFound();
    }

    @Test
    void getReaderIssuesSuccessTest() {
        Issue testIssue1 = new Issue();
        testIssue1.setBookId(testBook.getId());
        testIssue1.setReaderId(testReader.getId());
        Issue testIssue2 = new Issue();
        testIssue2.setBookId(testBook.getId());
        testIssue2.setReaderId(testReader.getId());
        issueRepository.saveAll(List.of(testIssue1, testIssue2));
        List<Issue> expected = issueRepository.findAll();

        List<IssueControllerTest.JUnitIssueResponse> responseBody =
        webTestClient.get()
                     .uri("/reader/" + testReader.getId() + "/issue")
                     .exchange()
                     .expectStatus().isOk()
                     .expectBody(new ParameterizedTypeReference<List<IssueControllerTest.JUnitIssueResponse>>() {
                     })
                     .returnResult().getResponseBody();

        Assertions.assertNotNull(responseBody);
        Assertions.assertEquals(expected.size(), responseBody.size());
        for (IssueControllerTest.JUnitIssueResponse customerResponse : responseBody) {
            boolean found = expected.stream()
                    .filter(it -> Objects.equals(it.getId(), customerResponse.getId()))
                    .anyMatch(it -> Objects.equals(it.getBookId(), customerResponse.getBookId()) &&
                                    Objects.equals(it.getReaderId(), customerResponse.getReaderId()) &&
                                    Objects.equals(it.getIssuedAt(), customerResponse.getIssuedAt()) &&
                                    Objects.equals(it.getReturnedAt(), customerResponse.getReturnedAt()));
            Assertions.assertTrue(found);
        }
    }

    @Test
    void getReaderIssuesNoContentTest() {
        webTestClient.get()
                     .uri("/reader/" + testReader.getId() + "/issue")
                     .exchange()
                     .expectStatus().isNoContent();
    }

    @Test
    void getReaderIssuesNotFoundTest() {
        Long maxId = jdbcTemplate.queryForObject("select max(id) from reader", Long.class);
        if (maxId == null) {
            maxId = 1L;
        } else {
            maxId += 1L;
        }

        webTestClient.get()
                .uri("/reader/" + maxId + "/issue")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void addReaderTest() {
        JUnitReaderResponse responseBody =
                webTestClient.post()
                             .uri("/reader")
                             .bodyValue(testReader)
                             .exchange()
                             .expectStatus().isCreated()
                             .expectBody(JUnitReaderResponse.class)
                             .returnResult().getResponseBody();

        Assertions.assertNotNull(responseBody);
        Assertions.assertNotNull(responseBody.getId());
        Assertions.assertEquals(responseBody.getName(), testReader.getName());
        Assertions.assertTrue(readerRepository.findById(responseBody.getId()).isPresent());
    }


    @Test
    void removeReaderSuccessTest() {
        webTestClient.delete()
                     .uri("/reader/" + testReader.getId())
                     .exchange()
                     .expectStatus().isNoContent();
    }

    @Test
    void removeBookByIdNotFoundTest() {
        Long maxId = jdbcTemplate.queryForObject("select max(id) from reader", Long.class);
        if (maxId == null) {
            maxId = 1L;
        } else {
            maxId += 1L;
        }

        webTestClient.delete()
                     .uri("/reader/" + maxId)
                     .exchange()
                     .expectStatus().isNotFound();
    }
}