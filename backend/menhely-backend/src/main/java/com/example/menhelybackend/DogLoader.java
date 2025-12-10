package com.example.menhelybackend;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

// @Component > Spring automatikusan legyártja a classt amikor meghívja egy másik
@Component
public class DogLoader {
    private final List<Dog> dogs;

    public DogLoader() {
        ObjectMapper mapper = new ObjectMapper(); // Jackson library - json read/write
        try (InputStream is = getClass().getResourceAsStream("/dogs.json")) {
            this.dogs = mapper.readValue(is, new TypeReference<List<Dog>>() {
            });
        } catch (IOException e) {
            throw new RuntimeException("Could not load dogs.json", e);
        }
    }

    public List<Dog> getDogs() {
        return dogs;
    }

    public Optional<Dog> findById(int id) {
        return dogs.stream()
                .filter(d -> d.getId() == id)
                .findFirst();
    }

    public void save() {
        ObjectMapper mapper = new ObjectMapper();
        try {

            String path = getClass().getResource("/dogs.json").getPath();


            mapper.writeValue(new java.io.File(path), this.dogs);

            System.out.println("Sikeresen mentve a fájlba: " + path);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Nem sikerült menteni a fájlba.", e);
        }
    }
}
