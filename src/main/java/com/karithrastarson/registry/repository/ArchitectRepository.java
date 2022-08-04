package com.karithrastarson.registry.repository;

import com.karithrastarson.registry.entity.Architect;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface ArchitectRepository extends JpaRepository<Architect, Long>, JpaSpecificationExecutor<Architect> {

    Optional<Architect> findByFullNameAndDob(String name, String dob);

    List<Architect> findByFullNameContainingIgnoreCase(String substring);

    List<Architect> findByUniversityContainingIgnoreCase(String substring);

}
