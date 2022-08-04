/*
package com.karithrastarson.registry.service;

import com.karithrastarson.registry.entity.Architect;
import com.karithrastarson.registry.exception.BadRequestException;
import com.karithrastarson.registry.exception.DuplicateException;
import com.karithrastarson.registry.exception.NoItemFoundException;
import com.karithrastarson.registry.repository.ArchitectRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ArchitectServiceTest {

    @Mock
    ArchitectRepository architectRepository;

    @InjectMocks
    ArchitectService architectService;

    @Test
    public void when_add_architect_it_should_return_architect() throws Exception{
        Architect john = new Architect("John", "13-01-1992", "DTU");
        when(architectRepository.save(any(Architect.class))).thenReturn(john);

        Architect created = architectService.addArchitect("John", "DTU", "13-01-1992");

        assertThat(created.equals(john));
    }

    @Test
    public void when_parameters_missing_error_is_thrown() throws Exception {
        try {
            architectService.addArchitect("", "DTU", "13-01-1992");
        }  catch (BadRequestException e) {
            assertThat(e.getMessage().equals("Parameter name missing or malformed"));
        }

        try {
            architectService.addArchitect("John", "DTU", "");
        }  catch (BadRequestException e) {
            assertThat(e.getMessage().equals("Parameter date of birth missing or malformed"));
        }
    }

    @Test
    public void when_architect_exists_duplicate_exception_is_thrown() throws Exception {
        Optional<Architect> john = Optional.of(new Architect("John", "13-01-1992", "DTU"));
        when(architectRepository.findByFullNameAndDob(eq("John"), eq("13-01-1992"))).thenReturn(john);
        try {
            architectService.addArchitect("John", "DTU", "13-01-1992");
        } catch (DuplicateException e) {
            assertThat(e.getMessage().equals("Item with identifier John already present in db"));
        }
    }

    @Test
    public void when_architect_is_present_it_is_returned() throws Exception {
        Architect john = new Architect("John", "13-01-1992", "DTU");
        Optional<Architect> optional = Optional.of(john);
        when(architectRepository.findById(eq(john.getId()))).thenReturn(optional);

        Architect result = architectService.getArchitectById(john.getId().toString());
        assertThat(result.equals(john));
    }

    @Test
    public void when_no_architect_is_found_throw_exception() {
        Optional<Architect> emptyOptional = Optional.empty();
        when(architectRepository.findById(any(Long.class))).thenReturn(emptyOptional);

        try {
            architectService.getArchitectById("0");
        } catch (NoItemFoundException e) {
            assertThat(e.getMessage().equals("Item with ID 9 not found"));
        }
    }
}
*/
