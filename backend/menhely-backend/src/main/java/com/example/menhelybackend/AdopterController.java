package com.example.menhelybackend;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
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


    @PutMapping("/adopters/{id}")
    public ResponseEntity<Adopter> updateAdopter(
            @PathVariable int id,
            @RequestBody Adopter details
    ) {
        Optional<Adopter> optional = adopterLoader.findById(id);

        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Adopter adopter = optional.get();

        // adopter.setName(details.getName());
        adopter.setPhone(details.getPhone());
        // adopter.setPreferredDog(details.getPreferredDog());

        adopterLoader.save();  // JSON mentése

        return ResponseEntity.ok(adopter);
    }

    @PostMapping("/adopters")
    public ResponseEntity<Adopter> addAdopter(@RequestBody Adopter newAdopter) {
        List<Adopter> list = adopterLoader.getAdopters();

        int newId = list.stream().mapToInt(Adopter::getId).max().orElse(0) + 1;
        newAdopter.setId(newId);

        list.add(newAdopter);
        adopterLoader.save();

        return ResponseEntity.ok(newAdopter);
    }

    @DeleteMapping("/adopters/{id}")
    public ResponseEntity<Void> deleteAdopter(@PathVariable int id) {
        List<Adopter> list = adopterLoader.getAdopters();

        boolean removed = list.removeIf(adopter -> adopter.getId() == id);

        if (removed) {
            adopterLoader.save();
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
