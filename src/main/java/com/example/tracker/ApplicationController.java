package com.example.tracker;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/applications")
public class ApplicationController {

    // In-memory store (no database needed for this assignment)
    private final Map<Long, InternshipApplication> store = new ConcurrentSkipListMap<>();
    private final AtomicLong seq = new AtomicLong(0);

    @GetMapping
    public List<InternshipApplication> getAll() {
        return new ArrayList<>(store.values());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InternshipApplication add(@RequestBody InternshipApplication app) {
        if (isBlank(app.getCompany())) badRequest("company must not be empty");
        if (isBlank(app.getRole())) badRequest("role must not be empty");
        app.setStatus(app.getStatus() == null ? "Applied" : normalizeStatus(app.getStatus()));
        app.setId(seq.incrementAndGet());
        store.put(app.getId(), app);
        return app;
    }

    @GetMapping("/{id}")
    public ResponseEntity<InternshipApplication> getById(@PathVariable Long id) {
        InternshipApplication app = store.get(id);
        return app == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(app);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<InternshipApplication> updateStatus(@PathVariable Long id,
                                                              @RequestBody Map<String, String> body) {
        InternshipApplication app = store.get(id);
        if (app == null) return ResponseEntity.notFound().build();
        app.setStatus(normalizeStatus(body == null ? null : body.get("status")));
        return ResponseEntity.ok(app);
    }

    private static final List<String> VALID_STATUSES = List.of("Applied", "Interview", "Rejected", "Offer");

    private static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }

    private static String normalizeStatus(String status) {
        if (!isBlank(status)) {
            for (String valid : VALID_STATUSES) {
                if (valid.equalsIgnoreCase(status.trim())) return valid;
            }
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "status must be one of " + VALID_STATUSES);
    }

    private static void badRequest(String message) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, message);
    }
}
