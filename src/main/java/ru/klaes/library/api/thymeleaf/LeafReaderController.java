package ru.klaes.library.api.thymeleaf;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.klaes.library.service.IssueService;
import ru.klaes.library.service.ReaderService;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/ui")
@RequiredArgsConstructor
public class LeafReaderController {

    private final ReaderService readerService;
    private final IssueService issueService;

    @GetMapping("/reader")
    public String books(Model model) {
        model.addAttribute("listReaders", readerService.getAllReaders());
        return "readers";
    }

    @GetMapping("/reader/{id}")
    public String getReader(@PathVariable long id, Model model) {
        model.addAllAttributes(new HashMap<>(Map.of("currentReader", readerService.getReaderById(id),
                                                    "listIssuedBooks", issueService.getIssuedBookByReaderId(id))));
        return "reader";
    }
}