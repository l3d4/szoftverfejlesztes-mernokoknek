package com.example.menhelybackend;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@RestController
@CrossOrigin
public class DogController {
    private final DogLoader dogLoader;

    public DogController(DogLoader dogLoader) {
        this.dogLoader = dogLoader;
    } // dependency injection - DogLoader @Component - automatikusan kreálódik

    @GetMapping("/dogs")
    public List<Dog> getAllDogs() {
        return dogLoader.getDogs();
    }

    @GetMapping("/dogs/{id}")
    public ResponseEntity<Dog> getDogById(@PathVariable int id) {
        return dogLoader.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
