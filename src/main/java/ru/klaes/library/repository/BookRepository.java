package ru.klaes.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.klaes.library.model.Book;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("select b from Book b cross join Issue i where i.readerId = :id and i.returnedAt is null")
    List<Book> findIssuedBookByReaderId(Long id);

}
