package ru.klaes.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.klaes.library.model.Reader;

@Repository
public interface ReaderRepository extends JpaRepository<Reader, Long> {

}
