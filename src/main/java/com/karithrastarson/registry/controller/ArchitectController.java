package com.karithrastarson.registry.controller;

import com.karithrastarson.registry.entity.Architect;
import com.karithrastarson.registry.exception.DuplicateException;
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

    /**
     * Endpoint: Get architect by ID
     *
     * @return Architect
     */
    @Tag(name = "Architect")
    @GetMapping(path = "/{id}")
    public @ResponseBody
    ResponseEntity<Architect> getArchitectById(@PathVariable("id") String id) {
        Architect architect = architectService.getArchitectById(id);
        if (architect == null) {
            return new ResponseEntity("Architect with id " + id + " not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(architect, HttpStatus.OK);
    }

    /**
     * Endpoint: Get assets related to an architect
     *
     * @return List of assets
     */
    @Tag(name = "Architect")
    @GetMapping(path = "/{id}/assets")
    public @ResponseBody
    ResponseEntity<List<String>> getArchitectAssets(@PathVariable("id") String id) {
        List<String> assetLinks = architectService.getArchitectAssets(id);
        if (assetLinks.isEmpty()) {
            return new ResponseEntity("No assets found for architect with id " + id, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(assetLinks, HttpStatus.OK);
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
