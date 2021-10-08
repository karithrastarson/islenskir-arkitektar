package com.karithrastarson.registry.service;

import com.karithrastarson.registry.entity.Architect;
import com.karithrastarson.registry.entity.Asset;
import com.karithrastarson.registry.exception.DuplicateException;
import com.karithrastarson.registry.repository.ArchitectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ArchitectService {
    @Autowired
    ArchitectRepository architectRepository;

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

    public Architect getArchitectById(String architectId) {
        try {
            long id = Long.parseLong(architectId);
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
    public List<String> getArchitectAssets(String id) {
        Optional<Architect> result = architectRepository.findById(Long.parseLong(id));
        if (!result.isPresent()) {
            return new ArrayList<String>();
        }
        Architect architect = result.get();
        List<Asset> assets = architect.getAssets();
        if (assets == null || assets.isEmpty()) {
            return new ArrayList<>();
        }
        List<String> links = new ArrayList<>();
        for (Asset asset : assets) {
            links.add(asset.getUrl());
        }
        return links;
    }
}
