package com.karithrastarson.registry.controller;

import com.karithrastarson.registry.entity.Asset;
import com.karithrastarson.registry.exception.UploadException;
import com.karithrastarson.registry.service.AssetService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(path = "/asset")
public class AssetController {

    @Autowired
    AssetService assetService;

    /**
     * Endpoint: Add asset to cloud storage
     *
     * @return Confirmation message
     */
    @Tag(name = "Asset")
    @PostMapping(path = "")
    public @ResponseBody
    ResponseEntity<String> uploadAsset(@RequestParam("file") MultipartFile file,
                                       @RequestParam("filename") String filename,
                                       @RequestParam("architectId") Long architectId,
                                       @RequestParam("buildingId") Long buildingId) {
        try {
            Asset asset = assetService.uploadAsset(filename, file, buildingId, architectId);
            return new ResponseEntity<>("Asset added to cloud with ID: " + asset.getId(), HttpStatus.OK);
        } catch (UploadException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }
}
