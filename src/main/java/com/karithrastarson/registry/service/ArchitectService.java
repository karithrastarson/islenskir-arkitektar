package com.karithrastarson.registry.service;

import com.karithrastarson.registry.entity.Architect;
import com.karithrastarson.registry.entity.Asset;
import com.karithrastarson.registry.exception.BadRequestException;
import com.karithrastarson.registry.exception.DuplicateException;
import com.karithrastarson.registry.exception.NoItemFoundException;
import com.karithrastarson.registry.repository.ArchitectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.karithrastarson.registry.utils.Validation.validateArchitect;

@Service
public class ArchitectService {
    @Autowired
    ArchitectRepository architectRepository;

    public Architect addArchitect(String name, String uni, String dob) throws DuplicateException, BadRequestException {
        validateArchitect(name, uni, dob, architectRepository);
        //Check if this entry already exists (same name and dob)

        Architect newArch = new Architect(name, dob, uni);
        architectRepository.save(newArch);
        return newArch;
    }

    public Architect getArchitectById(String architectId) throws NoItemFoundException {
        long id = Long.parseLong(architectId);
        Optional<Architect> architect = architectRepository.findById(id);
        if (!architect.isPresent()) {
            throw new NoItemFoundException(architectId);
        }
        return architect.get();
    }

    /**
    * Search architect, based on string input
    *
    * @param searchString the string to search for
     *
     * @return A list of architects matching the search string in some form
    * */
    public List<Architect> searchArchitect(String searchString, int page, int size) {
        List<Architect> results = architectRepository.findByFullNameContainingIgnoreCase(searchString);
        List<Architect> schoolResults = architectRepository.findByUniversityContainingIgnoreCase(searchString);
        if (schoolResults != null && !schoolResults.isEmpty()){
            results.addAll(schoolResults);
        }
        return results;
    }

    public Architect updateArchitectInfo(String architectId, Long profilePhoto, String uni) throws NoItemFoundException {
        Architect architect = getArchitectById(architectId);

        if (uni != null) {
            architect.setUniversity(uni);
        }
        architectRepository.save(architect);
        return architect;
    }

    public void removeArchitectInfo(String id) throws NoItemFoundException {
        Architect architect = getArchitectById(id);
        architectRepository.delete(architect);
    }
}
