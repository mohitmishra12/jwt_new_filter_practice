package com.example.feedback;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/feedback")
@RequiredArgsConstructor
public class FeedbackController {

    private final FeedbackRepository repo;

    // POST 7
    @PostMapping
    public ResponseEntity<Feedback> create(@RequestBody Feedback f) {
        return ResponseEntity.ok(repo.save(f));
    }

    // GET 11 - list feedbacks (this is extra; used as one of GETs)
    @GetMapping
    public ResponseEntity<?> list() {
        return ResponseEntity.ok(repo.findAll());
    }

    // DELETE 5
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!repo.existsById(id)) return ResponseEntity.notFound().build();
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

