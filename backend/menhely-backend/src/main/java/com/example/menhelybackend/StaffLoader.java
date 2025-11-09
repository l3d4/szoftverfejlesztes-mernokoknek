package com.example.menhelybackend;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@Component
public class StaffLoader {
    private final List<Staff> staff;

    public StaffLoader() {
        ObjectMapper mapper = new ObjectMapper();
        try (InputStream is = getClass().getResourceAsStream("/staff.json")) {
            this.staff = mapper.readValue(is, new TypeReference<List<Staff>>() {});
        } catch (IOException e) {
            throw new RuntimeException("Could not load staff.json", e);
        }
    }

    public List<Staff> getAll() {
        return staff;
    }

    public Optional<Staff> findById(int id) {
        return staff.stream().filter(s -> s.getId() == id).findFirst();
    }
}
