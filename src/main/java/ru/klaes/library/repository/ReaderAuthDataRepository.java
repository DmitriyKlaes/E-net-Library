package ru.klaes.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.klaes.library.model.ReaderAuthData;

import java.util.Optional;

@Repository
public interface ReaderAuthDataRepository extends JpaRepository<ReaderAuthData, Long> {
    Optional<ReaderAuthData> findByLogin(String login);

}
