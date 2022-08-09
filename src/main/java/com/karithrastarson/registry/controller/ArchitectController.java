package com.karithrastarson.registry.controller;

import com.karithrastarson.registry.entity.Architect;
import com.karithrastarson.registry.exception.BadRequestException;
import com.karithrastarson.registry.exception.DuplicateException;
import com.karithrastarson.registry.exception.NoItemFoundException;
import com.karithrastarson.registry.service.ArchitectService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

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
    @CrossOrigin(origins = "*")
    @PostMapping(path = "")
    public @ResponseBody
    ResponseEntity<String> addArchitect(@RequestBody ArchitectItem newArch) {
        try {
            Architect architect = architectService.addArchitect(newArch.getName(), newArch.getUni(), newArch.getDob());
            return new ResponseEntity<>("Architect added with ID " + architect.getId(), HttpStatus.CREATED);
        } catch (DuplicateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Endpoint: Get architect by ID
     *
     * @return Architect
     */
    @Tag(name = "Architect")
    @CrossOrigin(origins = "*")
    @GetMapping(path = "/{id}")
    public @ResponseBody
    ResponseEntity<Architect> getArchitectById(@PathVariable("id") String id) {
        try {
            Architect architect = architectService.getArchitectById(id);
            return new ResponseEntity<>(architect, HttpStatus.OK);
        } catch (NoItemFoundException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Endpoint: Update architect by ID
     *
     * @return Architect
     */
    @Tag(name = "Architect")
    @CrossOrigin(origins = "*")
    @PutMapping(path = "/{id}")
    public @ResponseBody
    ResponseEntity<Architect> updateArchitectInfo(@PathVariable("id") String id, @RequestBody ArchitectUpdatedInfo info ) {
        try {
            Architect architect = architectService.updateArchitectInfo(id, info.profilePhoto, info.uni);
            return new ResponseEntity<>(architect, HttpStatus.OK);
        } catch (NoItemFoundException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Endpoint: Delete architect by ID
     *
     * @return Architect
     */
    @Tag(name = "Architect")
    @CrossOrigin(origins = "*")
    @DeleteMapping(path = "/{id}")
    public @ResponseBody
    ResponseEntity<String> removeArchitectById(@PathVariable("id") String id ) {
        try {
            architectService.removeArchitectInfo(id);
            return new ResponseEntity<>("Success", HttpStatus.OK);
        } catch (NoItemFoundException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    public static class ArchitectItem {
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
    public static class ArchitectUpdatedInfo {
        private String uni;
        private Long profilePhoto;

        public ArchitectUpdatedInfo(String uni, Long profilePhoto) {
            this.uni = uni;
            this.profilePhoto = profilePhoto;
        }

        public String getUni() {
            return uni;
        }

        public Long getProfilePhoto() {
            return profilePhoto;
        }
    }
}
