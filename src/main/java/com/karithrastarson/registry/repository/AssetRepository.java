package com.karithrastarson.registry.repository;

import com.karithrastarson.registry.entity.Architect;
import org.springframework.data.repository.CrudRepository;

public interface AssetRepository extends CrudRepository<Architect, Long> {
}
