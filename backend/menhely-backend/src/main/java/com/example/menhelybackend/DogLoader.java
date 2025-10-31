package com.example.menhelybackend;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

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
}
