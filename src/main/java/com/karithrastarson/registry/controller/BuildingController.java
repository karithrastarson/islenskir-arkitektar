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
    @CrossOrigin(origins = "*")
    @PostMapping(path = "")
    public @ResponseBody
    ResponseEntity<String> addBuilding(@RequestBody BuildingItem newBuilding) {
        try {
            Building building = buildingService.addBuilding(newBuilding.getAddress(), newBuilding.getPostalCode(), newBuilding.getName(), newBuilding.getArchitectId(), newBuilding.getCreatedDate());
            return new ResponseEntity<>("Building added: " + building.getAddress(), HttpStatus.CREATED);
        } catch (DuplicateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
        catch (NoItemFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Endpoint: Update building information
     * Used to link and unlink architect as well
     *
     * @return Building information
     */
    @CrossOrigin(origins = "*")
    @Tag(name = "Building")
    @PutMapping(path = "/{id}")
    public @ResponseBody
    ResponseEntity<Building> updateBuildingById(@PathVariable("id") String id, @RequestBody BuildingItem updatedBuilding ) {
        try {
            Building building = buildingService.updateBuildingById(id, updatedBuilding.getName(), updatedBuilding.getArchitectId());
            return new ResponseEntity<>(building, HttpStatus.OK);
        } catch (NoItemFoundException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Endpoint: Get building by ID
     *
     * @return Building information
     */
    @Tag(name = "Building")
    @CrossOrigin(origins = "*")
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
        private int postalCode;
        private String name;
        private String architectId;
        private String createdDate;

        public BuildingItem(String address, int postalCode, String name, String architectId, String createdDate) {
            this.address = address;
            this.postalCode = postalCode;
            this.name = name;
            this.architectId = architectId;
            this.createdDate = createdDate;
        }
        public String getAddress() {
            return address;
        }
        public int getPostalCode() {
            return postalCode;
        }

        public String getCreatedDate() {
            return createdDate;
        }

        public String getName() {
            return name;
        }
        public String getArchitectId() {
            return architectId;
        }
    }
}
