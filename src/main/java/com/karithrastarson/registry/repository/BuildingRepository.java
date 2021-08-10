package com.karithrastarson.registry.repository;

import com.karithrastarson.registry.entity.Building;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface BuildingRepository extends CrudRepository<Building, Long> {

    Optional<Building> findByAddress(String address);
}
