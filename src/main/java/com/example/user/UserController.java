package com.example.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository repo;

    // POST 1: create user (alternate to /auth/register)
    @PostMapping
    public ResponseEntity<User> create(@RequestBody User u) {
        return ResponseEntity.ok(repo.save(u));
    }

    // GET 1: list users
    @GetMapping
    public ResponseEntity<List<User>> list() {
        return ResponseEntity.ok(repo.findAll());
    }

    // GET 2: get by id
    @GetMapping("/{id}")
    public ResponseEntity<User> get(@PathVariable Long id) {
        return repo.findById(id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // PUT 1: update user
    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User in) {
        return repo.findById(id).map(u -> {
            u.setEmail(in.getEmail());
            u.setFullName(in.getFullName());
            u.setRole(in.getRole());
            repo.save(u);
            return ResponseEntity.ok(u);
        }).orElse(ResponseEntity.notFound().build());
    }

    // DELETE 1: delete user
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!repo.existsById(id)) return ResponseEntity.notFound().build();
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // GET 3: find by username
    @GetMapping("/by-username/{username}")
    public ResponseEntity<User> byUsername(@PathVariable String username) {
        return repo.findByUsername(username).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
