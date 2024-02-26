package ru.klaes.library.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import ru.klaes.library.api.IssueRequest;
import ru.klaes.library.model.Book;
import ru.klaes.library.model.Issue;
import ru.klaes.library.properties.ReaderProperties;
import ru.klaes.library.repository.BookRepository;
import ru.klaes.library.repository.IssueRepository;
import ru.klaes.library.repository.ReaderRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
@EnableConfigurationProperties(ReaderProperties.class)
public class IssueService {

  private final BookRepository bookRepository;
  private final ReaderRepository readerRepository;
  private final ReaderProperties readerProperties;
  private final IssueRepository issueRepository;

  public List<Issue> getAllIssues() {
    return issueRepository.findAll();
  }

  public Issue issue(IssueRequest request) {
    if (bookRepository.findById(request.getBookId()).isEmpty()) {
      throw new NoSuchElementException("Не найдена книга с идентификатором \"" + request.getBookId() + "\"");
    }
    if (readerRepository.findById(request.getReaderId()).isEmpty()) {
      throw new NoSuchElementException("Не найден читатель с идентификатором \"" + request.getReaderId() + "\"");
    }
    if(issueRepository.findActiveIssuesByReaderId(request.getReaderId()).size() >= readerProperties.getMaxAllowedBooks()) {
      throw new SecurityException("Читателя с идентификатором \"" + request.getReaderId() + "\" больше не может брать книги");
    } else {
      Issue issue = new Issue(request.getBookId(), request.getReaderId());
      issueRepository.save(issue);
      return issue;
    }
  }

  public Issue closeActiveIssue(Long id) {
    Issue currentIssue = issueRepository.findById(id).orElse(null);
    if (currentIssue == null) {
      return null;
    }
    if (currentIssue.getReturnedAt() == null) {
      currentIssue.closeIssue();
      issueRepository.save(currentIssue);
    }
    return currentIssue;
  }

  public Issue getIssueById(Long id) {
    return issueRepository.findById(id).orElse(null);
  }

  public List<Book> getIssuedBookByReaderId(Long id) {
    return bookRepository.findIssuedBookByReaderId(id);
  }

}
