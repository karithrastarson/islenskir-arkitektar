package com.karithrastarson.registry.service;

import com.karithrastarson.registry.entity.Architect;
import com.karithrastarson.registry.entity.Building;
import com.karithrastarson.registry.exception.DuplicateException;
import com.karithrastarson.registry.exception.NoItemFoundException;
import com.karithrastarson.registry.repository.BuildingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BuildingService {
    @Autowired
    BuildingRepository buildingRepository;

    @Autowired
    ArchitectService architectService;

    public Building addBuilding(String address, int postalCode, String name, String architectId, String createdDate) throws DuplicateException, NoItemFoundException {
        //Check if this entry already exists (same address)
        Optional<Building> optional = buildingRepository.findByAddressAndPostalCode(address, postalCode);
        if (optional.isPresent()) {
            throw new DuplicateException(address);
        }

        Building newBuilding = new Building(address, postalCode, name, createdDate);

        Architect architect = architectService.getArchitectById(architectId);
        newBuilding.setArchitect(architect);
        buildingRepository.save(newBuilding);
        return newBuilding;
    }

    public Building getBuildingById(String buildingId) throws NoItemFoundException {
        long id = Long.parseLong(buildingId);
        Optional<Building> building = buildingRepository.findById(id);
        if (!building.isPresent()) {
            throw new NoItemFoundException(buildingId);
        }
        return building.get();
    }

    public List<Building> searchBuilding(String searchString) {
        List<Building> nameResults = buildingRepository.findByNameContainingIgnoreCase(searchString);
        List<Building> addressResults = buildingRepository.findByAddressContainingIgnoreCase(searchString);
        if (addressResults != null && !addressResults.isEmpty()){
            nameResults.addAll(addressResults);
        }
        return nameResults;
    }

    public Building updateBuildingById(String id, String name, String architectId) throws NoItemFoundException {
        Building building = getBuildingById(id);
        if (name != null) {
            building.setName(name);
        }
        if (architectId != null) {
            Architect architect = architectService.getArchitectById(architectId);
            building.setArchitect(architect);
        }
        else {
            //Unlink architect function
            building.setArchitect(null);
        }
        buildingRepository.save(building);
        return building;
    }
}
