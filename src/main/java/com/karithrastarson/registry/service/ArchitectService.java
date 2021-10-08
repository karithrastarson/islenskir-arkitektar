package com.karithrastarson.registry.service;

import com.karithrastarson.registry.entity.Architect;
import com.karithrastarson.registry.entity.Asset;
import com.karithrastarson.registry.exception.DuplicateException;
import com.karithrastarson.registry.repository.ArchitectRepository;
import com.karithrastarson.registry.repository.AssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ArchitectService {
    @Autowired
    ArchitectRepository architectRepository;

    @Autowired
    AssetRepository assetRepository;

    public Architect addArchitect(String name, String uni, String dob) throws DuplicateException {
        //Check if this entry already exists (same name and dob)
        List<Architect> results = architectRepository.findByFullNameAndDob(name, dob);
        if (!results.isEmpty()) {
            throw new DuplicateException(name);
        }

        Architect newArch = new Architect(name, uni, dob);
        architectRepository.save(newArch);
        return newArch;
    }

    public Architect getArchitectById(Long id) {
        try {
            return architectRepository.findById(id).orElse(null);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Fetch all assets linked to an architect
     *
     * @param id The id of the architect
     * @return A list of links to assets
     */
    public List<String> getArchitectAssets(Long id) {
        Optional<Architect> results = architectRepository.findById(id);
        if(!results.isPresent()) {
            return null;
        }
        List<Long> assetIds = results.get().getAssets();
        if (assetIds == null) {
            return null;
        }
        List<String> urls = new ArrayList<>();
        for(long assetId : assetIds) {
            Optional<Asset> asset = assetRepository.findById(assetId);
            if (asset.isPresent()) {
                urls.add(asset.get().getUrl());
            }
        }
        return urls;
    }

    public void addAsset(Long architectId, long assetId) {
        Architect architect = architectRepository.findById(architectId).orElse(null);
        if (architect != null) {
            architect.addAsset(assetId);
            architectRepository.save(architect);
        }

    }
}
