package com.example.tracker;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.Map;

import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ApplicationControllerTest {

    private InternshipApplication sample() {
        InternshipApplication a = new InternshipApplication();
        a.setCompany("Microsoft");
        a.setRole("Software Engineer Intern");
        return a;
    }

    @Test
    void addAssignsIdAndDefaultStatus() {
        ApplicationController c = new ApplicationController();
        InternshipApplication saved = c.add(sample());
        assertEquals(1L, saved.getId());
        assertEquals("Applied", saved.getStatus());
    }

    @Test
    void getByIdReturnsSavedApplication() {
        ApplicationController c = new ApplicationController();
        c.add(sample());
        assertEquals("Microsoft", c.getById(1L).getBody().getCompany());
    }

    @Test
    void unknownIdReturns404() {
        ApplicationController c = new ApplicationController();
        assertEquals(HttpStatus.NOT_FOUND, c.getById(99L).getStatusCode());
    }

    @Test
    void statusCanBeUpdated() {
        ApplicationController c = new ApplicationController();
        c.add(sample());
        c.updateStatus(1L, Map.of("status", "Interview"));
        assertEquals("Interview", c.getById(1L).getBody().getStatus());
    }

    @Test
    void invalidStatusOnUpdateIsRejected() {
        ApplicationController c = new ApplicationController();
        c.add(sample());
        assertThrows(ResponseStatusException.class,
                () -> c.updateStatus(1L, Map.of("status", "Banana")));
    }

    @Test
    void invalidStatusOnAddIsRejected() {
        ApplicationController c = new ApplicationController();
        InternshipApplication a = sample();
        a.setStatus("Banana");
        assertThrows(ResponseStatusException.class, () -> c.add(a));
    }

    @Test
    void emptyCompanyOrRoleIsRejected() {
        ApplicationController c = new ApplicationController();
        InternshipApplication a = sample();
        a.setCompany("  ");
        assertThrows(ResponseStatusException.class, () -> c.add(a));
        InternshipApplication b = sample();
        b.setRole("");
        assertThrows(ResponseStatusException.class, () -> c.add(b));
    }
}
