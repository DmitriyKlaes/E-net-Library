package ru.klaes.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.klaes.library.model.Issue;

import java.util.List;
import java.util.Optional;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Long> {

    @Query("select i from Issue i where i.readerId = :id and i.returnedAt is null")
    List<Issue> findActiveIssuesByReaderId(Long id);

    @Query("select i from Issue i where i.id = :id and i.returnedAt is null")
    Optional<Issue> findActiveIssueById(Long id);

    List<Issue> findByReaderId(Long id);

}
