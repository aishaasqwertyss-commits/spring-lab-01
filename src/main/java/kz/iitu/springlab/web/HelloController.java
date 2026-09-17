package kz.iitu.springlab.web;

// Lab 01 REST controller
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class HelloController {

    @Value("${app.owner:unknown}")
    private String owner;

    @GetMapping("/hello")
    public Greeting hello(@RequestParam(defaultValue = "world") String name) {
        return new Greeting(
                "Hello, " + name + "!",
                owner,
                LocalDateTime.now()
        );
    }

    @GetMapping("/info")
    public Info info() {
        return new Info(
                owner,
                System.getProperty("java.version"),
                Runtime.getRuntime().availableProcessors()
        );
    }

    @GetMapping("/factorial")
    public Map<String, Object> factorial(@RequestParam(defaultValue = "0") int n) {
        if (n < 0 || n > 20) {
            return Map.of("error", "n must be between 0 and 20");
        }

        long result = 1;

        for (int i = 2; i <= n; i++) {
            result *= i;
        }

        return Map.of(
                "n", n,
                "factorial", result
        );
    }

    public record Greeting(
            String message,
            String owner,
            LocalDateTime timestamp
    ) {}

    public record Info(
            String owner,
            String javaVersion,
            int cpuCores
    ) {}
}