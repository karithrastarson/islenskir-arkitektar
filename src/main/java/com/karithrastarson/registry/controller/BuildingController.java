package com.karithrastarson.registry.controller;

import com.karithrastarson.registry.entity.Building;
import com.karithrastarson.registry.exception.DuplicateException;
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
            Building building = buildingService.addBuilding(newBuilding.getAddress(), newBuilding.getArchitectId(), newBuilding.getCreatedDate());
            return new ResponseEntity<>("Building added with ID: " + building.getId() , HttpStatus.OK);
        } catch (DuplicateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    private static class BuildingItem {
        private String address;
        private String createdDate;
        private Long architectId;

        public BuildingItem(String address, String createdDate, Long architectId) {
            this.address = address;
            this.createdDate = createdDate;
            this.architectId = architectId;
        }

        public Long getArchitectId() {
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
