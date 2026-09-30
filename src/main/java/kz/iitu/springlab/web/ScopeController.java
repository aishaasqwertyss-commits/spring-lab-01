package kz.iitu.springlab.web;

import kz.iitu.springlab.scope.TicketOffice;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ScopeController {

    private final ObjectProvider<TicketOffice> ticketOfficeProvider;

    public ScopeController(ObjectProvider<TicketOffice> ticketOfficeProvider) {
        this.ticketOfficeProvider = ticketOfficeProvider;
    }

    @GetMapping("/api/lab2/scope")
    public Map<String, String> scope() {

        TicketOffice first = ticketOfficeProvider.getObject();
        TicketOffice second = ticketOfficeProvider.getObject();

        return Map.of(
                "first", first.getId(),
                "second", second.getId(),
                "same", String.valueOf(first == second)
        );
    }
}