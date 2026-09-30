package kz.iitu.springlab.config;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
public class ConfigController {

    private final DateTimeFormatter formatter;

    public ConfigController(DateTimeFormatter formatter) {
        this.formatter = formatter;
    }

    @GetMapping("/api/lab2/config")
    public String config() {
        return LocalDateTime.now().format(formatter);
    }
}