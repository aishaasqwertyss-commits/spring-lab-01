package kz.iitu.springlab.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import kz.iitu.springlab.catalog.Book;
import kz.iitu.springlab.catalog.BookNotFoundException;
import kz.iitu.springlab.catalog.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Locale;

@Controller
public class BookPageController {

    private final BookService service;
    private final LocaleResolver localeResolver;

    public BookPageController(
            BookService service,
            LocaleResolver localeResolver) {
        this.service = service;
        this.localeResolver = localeResolver;
    }

    @GetMapping("/language")
    public String changeLanguage(
            @RequestParam String lang,
            HttpServletRequest request,
            HttpServletResponse response) {

        Locale locale;

        if ("ru".equalsIgnoreCase(lang)) {
            locale = Locale.forLanguageTag("ru");
        } else {
            locale = Locale.ENGLISH;
        }

        localeResolver.setLocale(request, response, locale);

        return "redirect:/books/new";
    }

    @GetMapping("/books")
    public String books(
            @RequestParam(required = false) String author,
            Model model) {

        List<Book> books = service.findAll(author);
        model.addAttribute("books", books);
        model.addAttribute("author", author);

        return "books/list";
    }

    @GetMapping("/books/{id}")
    public String bookDetails(
            @PathVariable long id,
            Model model) {

        Book book = service.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));

        model.addAttribute("book", book);

        return "books/details";
    }

    @GetMapping("/books/new")
    public String newForm(Model model) {
        model.addAttribute("form", new BookForm());
        model.addAttribute("genres", Genre.values());

        return "books/form";
    }

    @PostMapping("/books")
    public String create(
            @Valid @ModelAttribute("form") BookForm form,
            BindingResult binding,
            Model model,
            RedirectAttributes redirect) {

        if (binding.hasErrors()) {
            model.addAttribute("genres", Genre.values());
            return "books/form";
        }

        Book saved = service.create(
                new Book(
                        null,
                        form.getTitle(),
                        form.getAuthor(),
                        form.getYear()
                )
        );

        redirect.addFlashAttribute(
                "message",
                "Book \"" + saved.title() + "\" has been added."
        );

        return "redirect:/books";
    }
}