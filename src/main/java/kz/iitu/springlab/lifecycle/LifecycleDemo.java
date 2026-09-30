package kz.iitu.springlab.lifecycle;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class LifecycleDemo {

    private final List<String> events = new ArrayList<>();

    public LifecycleDemo() {
        events.add(LocalDateTime.now() + " 1. Constructor called");
    }

    @PostConstruct
    public void init() {
        events.add(LocalDateTime.now() + " 2. @PostConstruct executed");
        System.out.println("LIFECYCLE >> " + events.get(events.size() - 1));
    }

    @PreDestroy
    public void destroy() {
        events.add(LocalDateTime.now() + " 3. @PreDestroy executed");
        System.out.println("LIFECYCLE >> " + events.get(events.size() - 1));
    }

    public List<String> events() {
        return events;
    }
}