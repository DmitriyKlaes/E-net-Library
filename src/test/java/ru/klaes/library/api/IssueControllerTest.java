package ru.klaes.library.api;

import lombok.Data;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.reactive.server.WebTestClient;
import ru.klaes.library.JUnitSpringBootBase;
import ru.klaes.library.model.Book;
import ru.klaes.library.model.Issue;
import ru.klaes.library.model.Reader;
import ru.klaes.library.repository.BookRepository;
import ru.klaes.library.repository.IssueRepository;
import ru.klaes.library.repository.ReaderRepository;

import java.time.LocalDateTime;

class IssueControllerTest extends JUnitSpringBootBase {

    @Autowired
    WebTestClient webTestClient;
    @Autowired
    IssueRepository issueRepository;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    ReaderRepository readerRepository;
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Data
    static class JUnitIssueResponse {
        private Long id;
        private Long bookId;
        private Long readerId;
        private LocalDateTime issuedAt;
        private LocalDateTime returnedAt;
    }

    @Data
    static class JUnitIssueRequest {
        private Long readerId;
        private Long bookId;
    }

    @Test
    void getIssueByIdSuccessTest() {
        Issue testIssue = setUpDataBaseAndGetTestIssue();
        Issue expectedIssue = issueRepository.save(testIssue);

        JUnitIssueResponse responseBody =
                webTestClient.get()
                             .uri("/issue/" + expectedIssue.getId())
                             .exchange()
                             .expectStatus().isOk()
                             .expectBody(JUnitIssueResponse.class)
                             .returnResult().getResponseBody();

        Assertions.assertNotNull(responseBody);
        Assertions.assertEquals(expectedIssue.getId(), responseBody.getId());
        Assertions.assertEquals(expectedIssue.getBookId(), responseBody.getBookId());
        Assertions.assertEquals(expectedIssue.getReaderId(), responseBody.getReaderId());
        Assertions.assertEquals(expectedIssue.getIssuedAt(), responseBody.getIssuedAt());
        Assertions.assertNull(expectedIssue.getReturnedAt());
    }

    @Test
    void getIssueByIdNotFoundTest() {
        Long maxId = jdbcTemplate.queryForObject("select max(id) from issue", Long.class);
        if (maxId == null) {
            maxId = 1L;
        } else {
            maxId += 1L;
        }

        webTestClient.get()
                     .uri("/issue/" + maxId)
                     .exchange()
                     .expectStatus().isNotFound();
    }

    @Test
    void issueBookCreatedTest() {
        Issue testIssue = setUpDataBaseAndGetTestIssue();
        JUnitIssueRequest testIssueRequest = new JUnitIssueRequest();
        testIssueRequest.setBookId(testIssue.getBookId());
        testIssueRequest.setReaderId(testIssue.getReaderId());

        JUnitIssueResponse responseBody =
                webTestClient.post()
                             .uri("/issue")
                             .bodyValue(testIssueRequest)
                             .exchange()
                             .expectStatus().isCreated()
                             .expectBody(JUnitIssueResponse.class)
                             .returnResult().getResponseBody();

        Assertions.assertNotNull(responseBody);
        Assertions.assertNotNull(responseBody.getId());
        Assertions.assertEquals(testIssueRequest.getBookId(), responseBody.getBookId());
        Assertions.assertEquals(testIssueRequest.getReaderId(), responseBody.getReaderId());
        Assertions.assertNotNull(responseBody.getIssuedAt());
        Assertions.assertNull(responseBody.getReturnedAt());
        Assertions.assertTrue(issueRepository.findById(responseBody.getId()).isPresent());
    }

    @Test
    void issueBookConflictTest() {
        Issue testIssue1 = setUpDataBaseAndGetTestIssue();
        Issue testIssue2 = new Issue(testIssue1.getBookId(), testIssue1.getReaderId());
        Issue testIssue3 = new Issue(testIssue1.getBookId(), testIssue1.getReaderId());
        issueRepository.save(testIssue1);
        issueRepository.save(testIssue2);
        issueRepository.save(testIssue3);
        JUnitIssueRequest testIssueRequest = new JUnitIssueRequest();
        testIssueRequest.setBookId(testIssue1.getBookId());
        testIssueRequest.setReaderId(testIssue1.getReaderId());

        webTestClient.post()
                     .uri("/issue")
                     .bodyValue(testIssueRequest)
                     .exchange()
                     .expectStatus().isEqualTo(HttpStatus.CONFLICT);
    }

    @Test
    void issueBookNotFoundTest() {
        Issue testIssue = setUpDataBaseAndGetTestIssue();
        Long maxBookId = jdbcTemplate.queryForObject("select max(id) from book", Long.class);
        if (maxBookId == null) {
            maxBookId = 1L;
        }
        Long maxReaderId = jdbcTemplate.queryForObject("select max(id) from reader", Long.class);
        if (maxReaderId == null) {
            maxReaderId = 1L;
        }

        JUnitIssueRequest testIssueRequest = new JUnitIssueRequest();

        testIssueRequest.setBookId(maxBookId + 1);
        testIssueRequest.setReaderId(maxReaderId);
        webTestClient.post()
                     .uri("/issue")
                     .bodyValue(testIssueRequest)
                     .exchange()
                     .expectStatus().isNotFound();

        testIssueRequest.setBookId(maxBookId);
        testIssueRequest.setReaderId(maxReaderId + 1);
        webTestClient.post()
                     .uri("/issue")
                     .bodyValue(testIssueRequest)
                     .exchange()
                     .expectStatus().isNotFound();
    }

    @Test
    void returnBookSuccessTest() {
        Issue testIssue = setUpDataBaseAndGetTestIssue();
        Issue expectedIssue = issueRepository.save(testIssue);

        JUnitIssueResponse responseBody =
                webTestClient.put()
                             .uri("/issue/" + expectedIssue.getId())
                             .exchange()
                             .expectStatus().isOk()
                             .expectBody(JUnitIssueResponse.class)
                             .returnResult().getResponseBody();


        Assertions.assertNotNull(responseBody);
        Assertions.assertEquals(expectedIssue.getId(), responseBody.getId());
        Assertions.assertEquals(expectedIssue.getBookId(), responseBody.getBookId());
        Assertions.assertEquals(expectedIssue.getReaderId(), responseBody.getReaderId());
        Assertions.assertEquals(expectedIssue.getIssuedAt(), responseBody.getIssuedAt());
        Assertions.assertNotNull(responseBody.getReturnedAt());
        Assertions.assertTrue(issueRepository.findById(responseBody.getId()).isPresent());
    }

    @Test
    void returnBookNotFoundTest() {
        Long maxId = jdbcTemplate.queryForObject("select max(id) from issue", Long.class);
        if (maxId == null) {
            maxId = 1L;
        } else {
            maxId += 1L;
        }

        webTestClient.put()
                     .uri("/issue/" + maxId)
                     .exchange()
                     .expectStatus().isNotFound();
    }

    private Issue setUpDataBaseAndGetTestIssue() {
        Book testIssuedBook = new Book();
        testIssuedBook.setName("TestIssuedBook");
        Book expectedIssuedBook = bookRepository.save(testIssuedBook);
        Reader testIssuedReader = new Reader();
        testIssuedReader.setName("TestIssuedReader");
        Reader expectedIssuedReader = readerRepository.save(testIssuedReader);
        return new Issue(expectedIssuedBook.getId(), expectedIssuedReader.getId());
    }

}