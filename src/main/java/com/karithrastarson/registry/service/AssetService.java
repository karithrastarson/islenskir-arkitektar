package com.karithrastarson.registry.service;

import com.karithrastarson.registry.entity.Architect;
import com.karithrastarson.registry.entity.Asset;
import com.karithrastarson.registry.entity.Building;
import com.karithrastarson.registry.exception.NoItemFoundException;
import com.karithrastarson.registry.exception.UploadException;
import com.karithrastarson.registry.repository.AssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class AssetService {
    @Autowired
    AssetRepository assetRepository;

    @Autowired
    MinioService minioService;

    @Autowired
    BuildingService buildingService;

    public List<Asset> uploadAssets(MultipartFile[] files, String buildingId) throws UploadException, NoItemFoundException {
        List<Asset> success = new ArrayList<>();
        for(MultipartFile file : files) {
            try {
                String filename = file.getOriginalFilename();
                String url = minioService.defaultBaseFolder + "/" + filename;
                minioService.uploadFile(filename, file.getBytes());

                //Store asset meta data to db
                Asset newAsset = new Asset(filename, url);
                if (buildingId != null && !buildingId.isEmpty()) {
                    Building building = buildingService.getBuildingById(buildingId);
                    newAsset.setBuilding(building);
                }
                assetRepository.save(newAsset);
                success.add(newAsset);
            } catch (IOException e) {
                //TODO: Log.
            }
        }
        return success;
    }
}
