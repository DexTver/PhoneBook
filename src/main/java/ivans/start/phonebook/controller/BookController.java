// https://github.com/DexTver/PhoneBook/tree/master/src/main/java/ivans/start/phonebook

package ivans.start.phonebook.controller;


import ivans.start.phonebook.model.Contact;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
    public ResponseEntity<String> update(
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
    public ResponseEntity<String> deleteContact(
            @PathVariable("index") Integer index) {
        if (index < full_list.size()) {
            full_list.remove((int) index);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Index out of range");
    }

    // curl localhost:8080/contacts
    // curl localhost:8080/contacts?num=666
    // curl localhost:8080/contacts?sub=I
    // curl localhost:8080/contacts?number=1&limit=2
    @GetMapping("contacts")
    public ResponseEntity<List<Contact>> getContacts(
            @RequestParam(value = "num", required = false) String num,
            @RequestParam(value = "sub", required = false) String sub,
            @RequestParam(value = "number", required = false) Integer number,
            @RequestParam(value = "number", required = false) Integer limit) {
        if (number >= 0 && limit > 0) {
            return ResponseEntity.ok(full_list.stream().skip((long) limit * number).toList().stream().limit(limit).collect(Collectors.toList()));
        } else if (number < 0 || limit <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        if (num != null) {
            return ResponseEntity.ok(full_list.stream().filter(x -> x.getName().contains(num)).collect(Collectors.toList()));
        } else if (sub != null) {
            return ResponseEntity.ok(full_list.stream().filter(x -> x.getName().contains(sub)).collect(Collectors.toList()));
        } else {
            return ResponseEntity.ok(full_list);
        }
    }

    // curl localhost:8080/contacts/0
    @GetMapping("contacts/{index}")
    public ResponseEntity<String> getContact(
            @PathVariable("index") Integer index) {
        if (index < full_list.size()) {
            return ResponseEntity.ok(full_list.get(index).toString());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Index out of range");
    }

    // curl localhost:8080/contacts/sorted
    // curl localhost:8080/contacts/sorted?param=email
    @GetMapping("contacts/sorted")
    public ResponseEntity<List<Contact>> getSortedContacts(
            @RequestParam(value = "param", required = false) String param) {
        if (Objects.equals(param, "email")) {
            return ResponseEntity.ok(full_list.stream().filter(x -> x.getEmail() != null).toList().stream().sorted(Comparator.comparing(Contact::getEmail)).collect(Collectors.toList()));
        } else if (Objects.equals(param, "number")) {
            return ResponseEntity.ok(full_list.stream().sorted(Comparator.comparing(Contact::getNumber)).collect(Collectors.toList()));
        } else {
            return ResponseEntity.ok(full_list.stream().sorted(Comparator.comparing(Contact::getName)).collect(Collectors.toList()));
        }
    }
}
