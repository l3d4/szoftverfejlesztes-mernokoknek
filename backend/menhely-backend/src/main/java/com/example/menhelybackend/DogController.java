package com.example.menhelybackend;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;


@RestController
public class DogController {
    private final DogLoader dogLoader;

    public DogController(DogLoader dogLoader) {
        this.dogLoader = dogLoader;
    } // dependency injection - DogLoader @Component - automatikusan kreálódik

    @GetMapping("/dogs")
    public List<Dog> getAllDogs() {
        return dogLoader.getDogs();
    }
}
