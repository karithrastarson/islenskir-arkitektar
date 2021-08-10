package com.karithrastarson.registry.controller;

import com.karithrastarson.registry.entity.Architect;
import com.karithrastarson.registry.exception.DuplicateException;
import com.karithrastarson.registry.service.ArchitectService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/architect")
public class ArchitectController {

    @Autowired
    ArchitectService architectService;

    /**
     * Endpoint: Add architect to db
     *
     * @return Confirmation message
     */
    @Tag(name = "Architect")
    @PostMapping(path = "")
    public @ResponseBody
    ResponseEntity<String> addArchitect(@RequestBody ArchitectItem newArch) {
        try {
            Architect architect = architectService.addArchitect(newArch.getName(), newArch.getUni(), newArch.getDob());
            return new ResponseEntity<>("Architect added with ID " + architect.getId(), HttpStatus.OK);
        } catch (DuplicateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    private static class ArchitectItem {
        private String name;
        private String dob;
        private String uni;

        public ArchitectItem(String name, String dob, String uni) {
            this.name = name;
            this.dob = dob;
            this.uni = uni;
        }

        public String getName() {
            return name;
        }

        public String getDob() {
            return dob;
        }

        public String getUni() {
            return uni;
        }

    }
}
