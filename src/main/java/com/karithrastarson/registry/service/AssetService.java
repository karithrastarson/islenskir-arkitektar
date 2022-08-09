package com.karithrastarson.registry.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.karithrastarson.registry.entity.Asset;
import com.karithrastarson.registry.entity.Building;
import com.karithrastarson.registry.exception.NoItemFoundException;
import com.karithrastarson.registry.exception.UploadException;
import com.karithrastarson.registry.repository.AssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class AssetService {
    public static final String BUCKET_NAME = "arkitektabucket";
    @Autowired
    AssetRepository assetRepository;

    @Autowired
    MinioService minioService;

    @Autowired
    BuildingService buildingService;

    @Autowired
    AmazonS3 amazonS3Client;

    public List<Asset> uploadAssets(MultipartFile[] files, String buildingId) throws UploadException, NoItemFoundException {
        List<Asset> success = new ArrayList<>();
        for(MultipartFile file : files) {
            try {
                ObjectMetadata metadata = new ObjectMetadata();
                metadata.setContentLength(file.getSize());
                amazonS3Client.putObject(BUCKET_NAME, file.getOriginalFilename(), file.getInputStream(), metadata);

                //Store asset meta data to db
                Asset newAsset = new Asset(file.getName(), BUCKET_NAME+ "/" + file.getOriginalFilename());
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
