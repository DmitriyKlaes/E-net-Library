package ru.klaes.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.klaes.library.aspect.Timer;
import ru.klaes.library.model.Issue;
import ru.klaes.library.model.Reader;
import ru.klaes.library.repository.ReaderRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReaderService {

    private final ReaderRepository readerRepository;

    @Timer
    public List<Reader> getAllReaders() {
        return readerRepository.findAll();
    }

    @Timer
    public Reader getReaderById(Long id) {
        return readerRepository.findById(id).orElse(null);
    }

    @Timer
    public void addReader(Reader reader) {
        readerRepository.save(reader);
    }

    public void removeReaderById(Long id) {
        readerRepository.deleteById(id);
    }

    public List<Issue> getIssueByReaderId(Long id) {
        return readerRepository.findById(id).map(Reader::getListIssues).orElse(null);
    }

}
