package com.karithrastarson.registry.controller;

import com.karithrastarson.registry.entity.Architect;
import com.karithrastarson.registry.entity.Building;
import com.karithrastarson.registry.service.ArchitectService;
import com.karithrastarson.registry.service.BuildingService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/search")
public class SearchController {

    @Autowired
    ArchitectService architectService;

    @Autowired
    BuildingService buildingService;

    /**
     * Endpoint: search an architect
     *
     * @return List of architect results or empty list
     */
    @Tag(name = "Search")
    @CrossOrigin(origins = "*")
    @PostMapping(path = "/architect")
    public @ResponseBody
    ResponseEntity<List<Architect>> searchArchitect(@RequestBody String searchString, @RequestParam int page, @RequestParam int size) {
        List<Architect> results = architectService.searchArchitect(searchString, page, size);
        if (results.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(results,HttpStatus.OK);
    }

    /**
     * Endpoint: search a building
     *
     * @return List of building results or empty list
     */
    @Tag(name = "Search")
    @CrossOrigin(origins = "*")
    @PostMapping(path = "/building")
    public @ResponseBody
    ResponseEntity<List<Building>> searchBuilding(@RequestBody String searchString) {
        List<Building> results = buildingService.searchBuilding(searchString);
        if (results.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(results,HttpStatus.OK);
    }
}
