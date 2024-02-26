package ru.klaes.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.klaes.library.aspect.Timer;
import ru.klaes.library.model.Book;
import ru.klaes.library.repository.BookRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Timer
public class BookService {

    private final BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(long id) {
        return bookRepository.findById(id).orElse(null);
    }

    public void addBook(Book book) {
        bookRepository.save(book);
    }

    public void removeBookById(long id) {
        bookRepository.deleteById(id);
    }

}
