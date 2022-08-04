package com.karithrastarson.registry.utils;

import com.karithrastarson.registry.entity.Architect;
import com.karithrastarson.registry.exception.BadRequestException;
import com.karithrastarson.registry.exception.DuplicateException;
import com.karithrastarson.registry.repository.ArchitectRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;



public class Validation {

    public static void validateArchitect(String name, String uni, String dob, ArchitectRepository architectRepository) throws DuplicateException, BadRequestException {
        //Check if parameters are missing or blank
        if (name == null || name.isEmpty()) {
            throw new BadRequestException("name");
        }
        if (dob == null || dob.isEmpty()) {
            throw new BadRequestException("date of birth");
        }

        //Check for duplicates
        Optional<Architect> result = architectRepository.findByFullNameAndDob(name, dob);
        if (result.isPresent()) {
            throw new DuplicateException(name);
        }
    }
}
