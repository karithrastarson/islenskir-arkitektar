package com.karithrastarson.registry.controller;

import com.karithrastarson.registry.entity.Asset;
import com.karithrastarson.registry.exception.NoItemFoundException;
import com.karithrastarson.registry.exception.UploadException;
import com.karithrastarson.registry.service.AssetService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(path = "/asset")
public class AssetController {

    //access AKIAS3U2YTOQP4IIX5GI
    // secret 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
    @Autowired
    AssetService assetService;

    /**
     * Endpoint: Add asset to cloud storage
     *
     * @return Confirmation message
     */
    @CrossOrigin(origins = "*")
    @Tag(name = "Asset")
    @PostMapping(path = "")
    public @ResponseBody
    ResponseEntity<List<Asset>> uploadAsset(@RequestParam("files") MultipartFile[] files,
                                            @RequestParam("buildingId") String buildingId) {
        try {
            List<Asset> assets = assetService.uploadAssets(files, buildingId);
            return new ResponseEntity<>(assets, HttpStatus.CREATED);
        } catch (UploadException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.CONFLICT);
        } catch (NoItemFoundException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
