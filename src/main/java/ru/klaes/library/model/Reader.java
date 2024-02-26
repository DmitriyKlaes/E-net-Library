package ru.klaes.library.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "reader")
@Data
@NoArgsConstructor
public class Reader {

  @Schema(description = "'ID' читателя",
          accessMode = Schema.AccessMode.READ_ONLY)
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Schema(description = "Имя читателя")
  @Column(name = "name")
  private String name;

  @Schema(description = "Список выдачей этого читателя",
          accessMode = Schema.AccessMode.READ_ONLY)
  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "reader_id")
  private List<Issue> listIssues = new ArrayList<>();

}
