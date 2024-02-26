package ru.klaes.library.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "issue")
@Data
@NoArgsConstructor
public class Issue {

  @Schema(description = "'ID' выдачи")
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Schema(description = "'ID' книги")
  @Column(name = "book_id")
  private Long bookId;

  @Schema(description = "'ID' читателя")
  @Column(name = "reader_id")
  private Long readerId;

  @Schema(description = "Время выдачи книги (при создании будет текущее время)")
  @Column(name = "issued_at")
  private LocalDateTime issuedAt;

  @Schema(description = "Время возвращения книги (при создании будет null)")
  @Column(name = "returned_at")
  private LocalDateTime returnedAt;

  public Issue(Long bookId, Long readerId) {
    this.bookId = bookId;
    this.readerId = readerId;
    this.issuedAt = LocalDateTime.now().withNano(0);
  }

  public void closeIssue() {
    this.returnedAt = LocalDateTime.now().withNano(0);
  }

  public String formatDate(LocalDateTime timeToFormat) {
    if (timeToFormat == null) {
      return "";
    }
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    return timeToFormat.format(formatter);
  }

}
