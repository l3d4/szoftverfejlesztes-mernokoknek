package com.example.menhelybackend;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StaffController {
     private final StaffLoader staffLoader;

     public StaffController(StaffLoader staffLoader) {
         this.staffLoader = staffLoader;
     }

     @GetMapping("/staff")
     public List<Staff> listStaff() {
         return staffLoader.getAll();
     }

     @GetMapping("/staff/{id}")
     public ResponseEntity<Staff> getStaffById(@PathVariable int id) {
         return staffLoader.findById(id)
                 .map(ResponseEntity::ok)
                 .orElseGet(() -> ResponseEntity.notFound().build());
     }
}