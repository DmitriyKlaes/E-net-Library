package ru.klaes.library.api.thymeleaf;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.klaes.library.service.BookService;

@Controller
@RequestMapping("/ui")
@RequiredArgsConstructor
public class LeafBookController {

    private final BookService bookService;
    @GetMapping("/book")
    public String books(Model model) {
        model.addAttribute("listBooks", bookService.getAllBooks());
        return "books";
    }
}