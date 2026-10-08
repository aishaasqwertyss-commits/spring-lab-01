package kz.iitu.springlab.controller;

import kz.iitu.springlab.annotation.RequiresRole;
import kz.iitu.springlab.service.CatalogService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/catalog")
public class CatalogController {

    private final CatalogService catalogService;

    public CatalogController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @GetMapping("/{id}")
    public String getItem(@PathVariable Long id) {
        return catalogService.getItem(id);
    }

    @GetMapping("/{id}/delete")
    @RequiresRole("ADMIN")
    public String deleteItem(@PathVariable Long id, @RequestParam String role) {
        return catalogService.deleteItem(id, role);
    }

    @GetMapping("/internal/{id}")
    public String testInternalCall(@PathVariable Long id) {
        catalogService.testSelfInvocation(id);
        return "Internal call completed. Check console output.";
    }
}