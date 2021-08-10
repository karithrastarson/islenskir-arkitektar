package com.karithrastarson.registry.repository;

import com.karithrastarson.registry.entity.Architect;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ArchitectRepository extends CrudRepository<Architect, Long> {

    List<Architect> findByFullNameAndDob(String name, String dob);
}
