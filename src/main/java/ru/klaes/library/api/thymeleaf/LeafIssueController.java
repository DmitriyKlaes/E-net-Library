package ru.klaes.library.api.thymeleaf;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.klaes.library.service.BookService;
import ru.klaes.library.service.IssueService;
import ru.klaes.library.service.ReaderService;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/ui")
@RequiredArgsConstructor
public class LeafIssueController {

    private final IssueService issuerService;
    private final BookService bookService;
    private final ReaderService readerService;

    @GetMapping("/issue")
    public String issues(Model model) {
        model.addAllAttributes(new HashMap<>(Map.of("issueService", issuerService,
                                                    "bookService", bookService,
                                                    "readerService", readerService)));
        return "issues";
    }
}