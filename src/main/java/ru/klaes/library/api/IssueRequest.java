package ru.klaes.library.api;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class IssueRequest {

  @Schema(description = "'ID' читателя")
  private Long readerId;

  @Schema(description = "'ID' книги")
  private Long bookId;

}
