package com.karithrastarson.registry.service;

import com.karithrastarson.registry.entity.Architect;
import com.karithrastarson.registry.exception.DuplicateException;
import com.karithrastarson.registry.repository.ArchitectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        if (architectId == null || architectId.isEmpty()) {
            return null;
        }
        return architectRepository.findById(Long.parseLong(architectId)).orElse(null);
    }
}
