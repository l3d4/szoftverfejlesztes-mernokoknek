package com.example.menhelybackend;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AdopterController {
    private final AdopterLoader adopterLoader;

    public AdopterController(AdopterLoader adopterLoader) {
        this.adopterLoader = adopterLoader;
    }

    @GetMapping("/adopters")
    public List<Adopter> listAdopters() {
        return adopterLoader.getAll();
    }

    @GetMapping("/adopters/{id}")
    public ResponseEntity<Adopter> getAdopterById(@PathVariable int id) {
        return adopterLoader.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}