package ru.klaes.library.api;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.klaes.library.api.annotation.swagger_description.issue.SwaggerIssueGetDescription;
import ru.klaes.library.api.annotation.swagger_description.issue.SwaggerIssuePostDescription;
import ru.klaes.library.api.annotation.swagger_description.issue.SwaggerIssuePutDescription;
import ru.klaes.library.model.Issue;
import ru.klaes.library.service.IssueService;

import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequestMapping("/issue")
@RequiredArgsConstructor
@Tag(name = "Выдачи", description = "Контроллер для управления выдачами")
public class IssueController {

  private final IssueService issueService;

  @SwaggerIssueGetDescription(summary = "Получить выдачу по 'ID'",
                              description = "Получить выдачу по идентификатору 'ID'.")
  @GetMapping("/{id}")
  public ResponseEntity<Issue> getIssueById(@PathVariable @Parameter(description = "'ID' выдачи") Long id) {
    Issue queryIssue = issueService.getIssueById(id);
    if (queryIssue == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(queryIssue);
  }

  @SwaggerIssuePostDescription(summary = "Выдача книги",
                               description = "Выдает книгу читателю. Нужен ID читателя и ID выдаваемой книги.")
  @PostMapping
  public ResponseEntity<Issue> issueBook(@RequestBody IssueRequest request) {
    log.info("Получен запрос на выдачу: readerId = {}, bookId = {}", request.getReaderId(), request.getBookId());

    final Issue issue;
    try {
      issue = issueService.issue(request);
    } catch (NoSuchElementException e) {
      return ResponseEntity.notFound().build();
    } catch (SecurityException e) {
      return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    return ResponseEntity.status(HttpStatus.CREATED).body(issue);
  }

  @SwaggerIssuePutDescription(summary = "Возвращение книги",
                              description = "Возвращает книгу назад в библиотеку, закрывая выдачу.")
  @PutMapping("/{id}")
  public ResponseEntity<Issue> returnBook(@PathVariable @Parameter(description = "'ID' выдачи.") Long id) {
    Issue closedIssue = issueService.closeActiveIssue(id);
    if (closedIssue == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(closedIssue);
  }


}
