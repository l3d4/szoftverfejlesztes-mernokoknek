package com.example.menhelybackend;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

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

    @PutMapping("/dogs/{id}")
    public ResponseEntity<Dog> updateDog(@PathVariable int id, @RequestBody Dog dogDetails) {
        Optional<Dog> optionalDog = dogLoader.findById(id);

        if (optionalDog.isPresent()) {
            Dog dog = optionalDog.get();
            dog.setName(dogDetails.getName());
            dog.setBreed(dogDetails.getBreed());
            dogLoader.save();
            return ResponseEntity.ok(dog);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/dogs/{id}")
    public ResponseEntity<Dog> getDogById(@PathVariable int id) {
        return dogLoader.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/dogs")
    public ResponseEntity<Dog> createDog(@RequestBody Dog dogDetails) {
        Dog createdDog = dogLoader.addDog(dogDetails);
        return ResponseEntity.ok(createdDog);
    }

    @DeleteMapping("/dogs/{id}")
    public ResponseEntity<Void> deleteDog(@PathVariable int id) {
        List<Dog> list = dogLoader.getDogs();


        boolean removed = list.removeIf(dog -> dog.getId() == id);

        if (removed) {
            dogLoader.save();
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
