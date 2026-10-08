package kz.iitu.springlab.service;

import kz.iitu.springlab.annotation.RequiresRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class CatalogService {

    @Autowired
    @Lazy
    private CatalogService self;

    public String getItem(Long id) {
        return "Item #" + id;
    }

    @RequiresRole("ADMIN")
    public String deleteItem(Long id, String role) {
        return "Item " + id + " deleted successfully.";
    }

    public void testSelfInvocation(Long id) {
        System.out.println("--- Direct Call via 'this' (AOP Bypassed) ---");
        this.deleteItem(id, "ADMIN");

        System.out.println("\n--- Proxy Call via 'self' (AOP Intercepted) ---");
        self.deleteItem(id, "ADMIN");
    }
}