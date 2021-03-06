package com.karithrastarson.registry.service;

import com.karithrastarson.registry.entity.Architect;
import com.karithrastarson.registry.entity.Asset;
import com.karithrastarson.registry.entity.Building;
import com.karithrastarson.registry.exception.DuplicateException;
import com.karithrastarson.registry.exception.NoItemFoundException;
import com.karithrastarson.registry.exception.UploadException;
import com.karithrastarson.registry.repository.AssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Service
public class AssetService {
    @Autowired
    AssetRepository assetRepository;

    @Autowired
    MinioService minioService;

    @Autowired
    BuildingService buildingService;

    @Autowired
    ArchitectService architectService;

    public Asset uploadAsset(String name, MultipartFile file, String buildingId, String architectId) throws UploadException, NoItemFoundException {
        try {
            minioService.uploadFile(name, file.getBytes());
            String url = minioService.defaultBaseFolder + "/" + file.getOriginalFilename();

            //Store asset meta data to db
            Asset newAsset = new Asset(name, url);
            if (buildingId != null && !buildingId.isEmpty()) {
                Building building = buildingService.getBuildingById(buildingId);
                newAsset.setBuilding(building);
            }
            if (architectId != null && !architectId.isEmpty()) {
                Architect architect = architectService.getArchitectById(architectId);
                newAsset.setArchitect(architect);
            }
            assetRepository.save(newAsset);
            return newAsset;
        } catch (IOException e) {
            throw new UploadException(name);
        }
    }
}
