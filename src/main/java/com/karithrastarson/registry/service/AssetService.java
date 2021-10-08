package com.karithrastarson.registry.service;

import com.karithrastarson.registry.entity.Asset;
import com.karithrastarson.registry.exception.UploadException;
import com.karithrastarson.registry.repository.AssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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

    public Asset uploadAsset(String name, MultipartFile file, Long buildingId, Long architectId) throws UploadException {
        try {
            minioService.uploadFile(name, file.getBytes());
            String url = minioService.defaultBaseFolder + "/" + file.getOriginalFilename();

            //Store asset meta data to db
            Asset newAsset = new Asset(name, url);
            if (buildingId != null) {
                newAsset.setBuilding(buildingId);
            }
            if (architectId != null) {
                newAsset.addArchitect(architectId);
            }
            assetRepository.save(newAsset);
            return newAsset;
        } catch (IOException e) {
            throw new UploadException(name);
        }
    }
}
