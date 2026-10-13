package kz.iitu.springlab.web;

import kz.iitu.springlab.catalog.Book;
import kz.iitu.springlab.catalog.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookRestController {

    private final BookService service;

    public BookRestController(BookService service) {
        this.service = service;
    }

    // GET /api/books
    @GetMapping
    public List<Book> list(
            @RequestParam(required = false) String author,
            @RequestParam(defaultValue = "10") int limit) {

        return service.findAll(author)
                .stream()
                .limit(limit)
                .toList();
    }

    // GET /api/books/{id}
    @GetMapping("/{id}")
    public ResponseEntity<?> find(@PathVariable long id) {

        return service.findById(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> {
                    org.springframework.http.ProblemDetail problem =
                            org.springframework.http.ProblemDetail.forStatusAndDetail(
                                    org.springframework.http.HttpStatus.NOT_FOUND,
                                    "Book with id " + id + " was not found."
                            );

                    problem.setTitle("Book Not Found");

                    return ResponseEntity.status(404).body(problem);
                });
    }

    // POST /api/books
    @PostMapping
    public ResponseEntity<Book> create(@RequestBody Book book) {

        Book saved = service.create(book);

        return ResponseEntity
                .created(URI.create("/api/books/" + saved.id()))
                .body(saved);
    }

    // PUT /api/books/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Book> update(
            @PathVariable long id,
            @RequestBody Book book) {

        if (service.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Book updated = service.update(id, book);

        return ResponseEntity.ok(updated);
    }

    // DELETE /api/books/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {

        if (!service.delete(id)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}