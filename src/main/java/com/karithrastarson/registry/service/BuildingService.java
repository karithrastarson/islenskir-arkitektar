package com.karithrastarson.registry.service;

import com.karithrastarson.registry.entity.Building;
import com.karithrastarson.registry.exception.DuplicateException;
import com.karithrastarson.registry.repository.BuildingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BuildingService {
    @Autowired
    BuildingRepository buildingRepository;

    public Building addBuilding(String address, Long architectId, String createdDate) throws DuplicateException {
        //Check if this entry already exists (same address)
        Optional<Building> optional = buildingRepository.findByAddress(address);
        if (optional.isPresent()) {
            throw new DuplicateException(address);
        }

        Building newBuilding = new Building(address, architectId, createdDate);
        buildingRepository.save(newBuilding);
        return newBuilding;
    }

    public Building getBuildingById(String buildingId) {
        try {
            long id = Long.parseLong(buildingId);
            return buildingRepository.findById(id).orElse(null);
        } catch (Exception e) {
            return null;
        }
    }
}
