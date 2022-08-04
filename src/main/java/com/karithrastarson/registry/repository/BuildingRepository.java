package com.karithrastarson.registry.repository;

import com.karithrastarson.registry.entity.Building;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface BuildingRepository extends CrudRepository<Building, Long> {

    Optional<Building> findByAddress(String address);

    List<Building> findByNameContainingIgnoreCase(String search);

    List<Building> findByAddressContainingIgnoreCase(String search);

    Optional<Building> findByAddressAndPostalCode(String address, int postalCode);
}
