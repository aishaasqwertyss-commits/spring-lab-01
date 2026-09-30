package kz.iitu.springlab.web;

import kz.iitu.springlab.lifecycle.LifecycleDemo;
import kz.iitu.springlab.notify.NotificationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/lab2")
public class Lab2Controller {

    private final NotificationService notifications;
    private final LifecycleDemo lifecycle;

    public Lab2Controller(
            NotificationService notifications,
            LifecycleDemo lifecycle
    ) {
        this.notifications = notifications;
        this.lifecycle = lifecycle;
    }

    @GetMapping("/notify")
    public Map<String, Object> notify(
            @RequestParam(name = "text", defaultValue = "Hello") String text
    ) {
        return Map.of(
                "primary", notifications.viaPrimary(text),
                "console", notifications.viaConsole(text),
                "all", notifications.viaAll(text),
                "beanNames", notifications.names()
        );
    }

    @GetMapping("/lifecycle")
    public Map<String, Object> lifecycle() {
        return Map.of(
                "events", lifecycle.events()
        );
    }

    @GetMapping("/custom")
    public String custom(
            @RequestParam(name = "text") String text
    ) {
        return notifications.viaCustom(text);
    }
}