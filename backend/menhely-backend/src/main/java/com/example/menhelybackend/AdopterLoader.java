package com.example.menhelybackend;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@Component
public class AdopterLoader {
    private final List<Adopter> adopters;

    public AdopterLoader() {
        ObjectMapper mapper = new ObjectMapper();
        try (InputStream is = getClass().getResourceAsStream("/adopters.json")) {
            this.adopters = mapper.readValue(is, new TypeReference<List<Adopter>>() {});
        } catch (IOException e) {
            throw new RuntimeException("Could not load adopters.json", e);
        }
    }

    public List<Adopter> getAll() {
        return adopters;
    }

    public Optional<Adopter> findById(int id) {
        return adopters.stream().filter(a -> a.getId() == id).findFirst();
    }
}
