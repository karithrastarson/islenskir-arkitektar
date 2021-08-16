package com.karithrastarson.registry.repository;

import com.karithrastarson.registry.entity.Asset;
import org.springframework.data.repository.CrudRepository;

public interface AssetRepository extends CrudRepository<Asset, Long> {
}
