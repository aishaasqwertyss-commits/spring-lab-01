package kz.iitu.springlab.scope;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class TicketOffice {

    private final String id = java.util.UUID.randomUUID().toString();

    public String getId() {
        return id;
    }
}