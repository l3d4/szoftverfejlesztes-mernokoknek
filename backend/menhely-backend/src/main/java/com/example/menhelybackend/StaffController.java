package com.example.menhelybackend;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
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

    @PostMapping("/staff")
    public ResponseEntity<Staff> addStaff(@RequestBody Staff newStaff) {
        List<Staff> list = staffLoader.getStaff();


        int newId = list.stream().mapToInt(Staff::getId).max().orElse(0) + 1;
        newStaff.setId(newId);

        list.add(newStaff);
        staffLoader.save();

        return ResponseEntity.ok(newStaff);
    }

    // MÓDOSÍTÁS (PUT)
    @PutMapping("/staff/{id}")
    public ResponseEntity<Staff> updateStaff(@PathVariable int id, @RequestBody Staff staffDetails) {
        Optional<Staff> op = staffLoader.findById(id);
        if (op.isPresent()) {
            Staff staff = op.get();

            staff.setFirstName(staffDetails.getFirstName());
            staff.setLastName(staffDetails.getLastName());
            staff.setRole(staffDetails.getRole());
            staff.setEmail(staffDetails.getEmail());
            staff.setPhone(staffDetails.getPhone());

            staffLoader.save();
            return ResponseEntity.ok(staff);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/staff/{id}")
    public ResponseEntity<Void> deleteStaff(@PathVariable int id) {
        List<Staff> list = staffLoader.getStaff();

        boolean removed = list.removeIf(staff -> staff.getId() == id);

        if (removed) {
            staffLoader.save();
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}