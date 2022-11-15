package ivans.start.phonebook.controller;


import ivans.start.phonebook.model.Contact;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RestController
public class BookController {
    private final List<Contact> full_list = new ArrayList<>();

    // curl -X POST localhost:8080/contacts -H "Content-Type: application/json" -d '{"name": "Ivan", "number": "8-800-555-35-35"}'
    @PostMapping("contacts")
    public ResponseEntity<Void> addContact(
            @RequestBody Contact info) {
        full_list.add(info);
        return ResponseEntity.accepted().build();
    }

    // curl -X POST localhost:8080/contacts/0/update -H "Content-Type: application/json" -d '{"name": "Ivan", "number": "8-800-555-35-35", "email": "example@gmail.com"}'
    @PostMapping("contacts/{index}/update")
    public ResponseEntity<String> updateAge(
            @PathVariable("index") Integer index,
            @RequestBody Contact info) {
        if (index < full_list.size()) {
            full_list.set(index, info);
            return ResponseEntity.accepted().build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Index out of range");
    }

    // curl -X DELETE localhost:8080/contacts/0
    @DeleteMapping("contacts/{index}")
    public ResponseEntity<String> deleteUser(
            @PathVariable("index") Integer index) {
        if (index < full_list.size()) {
            full_list.remove((int) index);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Index out of range");
    }

    // curl localhost:8080/contacts
    @GetMapping("contacts")
    public ResponseEntity<List<Contact>> getUsers() {
        return ResponseEntity.ok(full_list);
    }

    // curl localhost:8080/contacts/0
    @GetMapping("contacts/{index}")
    public ResponseEntity<String> getUser(
            @PathVariable("index") Integer index) {
        if (index < full_list.size()) {
            return ResponseEntity.ok(full_list.get(index).toString());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Index out of range");
    }
}
