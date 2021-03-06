package com.karithrastarson.registry.controller;

import com.karithrastarson.registry.entity.Architect;
import com.karithrastarson.registry.entity.Building;
import com.karithrastarson.registry.exception.DuplicateException;
import com.karithrastarson.registry.exception.NoItemFoundException;
import com.karithrastarson.registry.service.ArchitectService;
import com.karithrastarson.registry.service.BuildingService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/building")
public class BuildingController {

    @Autowired
    BuildingService buildingService;

    @Autowired
    ArchitectService architectService;

    /**
     * Endpoint: Add building to db
     *
     * @return Confirmation message
     */
    @Tag(name = "Building")
    @PostMapping(path = "")
    public @ResponseBody
    ResponseEntity<String> addBuilding(@RequestBody BuildingItem newBuilding) {
        try {
            Architect architect = architectService.getArchitectById(newBuilding.getArchitectId());
            Building building = buildingService.addBuilding(newBuilding.getAddress(), architect, newBuilding.getCreatedDate());
            return new ResponseEntity<>("Building added with id " + building.getId(), HttpStatus.CREATED);
        } catch (DuplicateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (NoItemFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Endpoint: Get building by ID
     *
     * @return Building information
     */
    @Tag(name = "Building")
    @GetMapping(path = "/{id}")
    public @ResponseBody
    ResponseEntity<Building> getBuildingById(@PathVariable("id") String id) {
        try {
            Building building = buildingService.getBuildingById(id);
            return new ResponseEntity<>(building, HttpStatus.OK);
        } catch (NoItemFoundException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    private static class BuildingItem {
        private String address;
        private String createdDate;
        private String architectId;

        public BuildingItem(String address, String createdDate, String architectId) {
            this.address = address;
            this.createdDate = createdDate;
            this.architectId = architectId;
        }

        public String getArchitectId() {
            return architectId;
        }

        public String getAddress() {
            return address;
        }

        public String getCreatedDate() {
            return createdDate;
        }
    }
}
