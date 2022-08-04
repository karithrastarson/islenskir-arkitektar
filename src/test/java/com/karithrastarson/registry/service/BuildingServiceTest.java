/*
package com.karithrastarson.registry.service;

import com.karithrastarson.registry.entity.Architect;
import com.karithrastarson.registry.entity.Building;
import com.karithrastarson.registry.exception.BadRequestException;
import com.karithrastarson.registry.exception.DuplicateException;
import com.karithrastarson.registry.exception.NoItemFoundException;
import com.karithrastarson.registry.repository.BuildingRepository;
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
public class BuildingServiceTest {

    @Mock
    BuildingRepository buildingRepository;

    @InjectMocks
    BuildingService buildingService;

    @Test
    public void when_building_is_added_building_is_returned() throws DuplicateException {
        Architect johnDoe = new Architect("John Doe", "13-01-1900", "DTU");
        Building building = new Building("Main street 1", johnDoe, "13-01-1950");
        when(buildingRepository.save(any(Building.class))).thenReturn(building);

        Building added = buildingService.addBuilding("Main street 1", johnDoe, "13-01-1950");

        assertThat(added.equals(building));
    }

    @Test
    public void when_building_exists_duplicate_exception_is_thrown() {
        Optional<Building> mainStreet = Optional.of(new Building("Main street 1", null, null));
        when(buildingRepository.findByAddress(eq("Main street 1"))).thenReturn(mainStreet);

        try {
            buildingService.addBuilding("Main street 1", null, null);
        } catch (DuplicateException e) {
            assertThat(e.getMessage().equals("Item with identifier Main street 1 already present in db"));
        }
    }

}
*/
