package com.romeo.birdssighting.unit.repositories;


import com.romeo.birdssighting.domain.Sighting;
import com.romeo.birdssighting.repositories.SightingRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SightingRepositoryTest {

    @Mock
    private SightingRepository sightingRepository;

    /**
     * This method is used for testing get all sightings find by bird id
     */
    @Test
    public void testFindByBirdId() {
        Long birdId = 1L;
        List<Sighting> sightings = new ArrayList<>();

        when(sightingRepository.findByBirdId(birdId)).thenReturn(sightings);

        var result = sightingRepository.findByBirdId(birdId);
        // Verify behavior
        assertEquals(sightings.size(), result.size());
        verify(sightingRepository).findByBirdId(birdId);
    }

    /**
     * This method is used for testing get all sightings find by bird location
     */
    @Test
    public void testFindByLocation() {
        var location = "Test";
        List<Sighting> sightings = new ArrayList<>();

        when(sightingRepository.findByLocation(location)).thenReturn(sightings);

        var result = sightingRepository.findByLocation(location);

        assertEquals(sightings.size(), result.size());
        verify(sightingRepository).findByLocation(location);
    }

    @Test
    public void testFindByDateTime() {
        var dateTime = LocalDateTime.now();
        List<Sighting> sightings = new ArrayList<>();
        // Mock behavior
        when(sightingRepository.findByDateTime(dateTime)).thenReturn(sightings);
        var result = sightingRepository.findByDateTime(dateTime);
        // Verify behavior
        assertEquals(sightings.size(), result.size());
        verify(sightingRepository).findByDateTime(dateTime);
    }

    /**
     * This method is used for testing delete a sighting find by id
     */
    @Test
    public void testDeleteByBirdId() {
        Long birdId = 1L;

        doNothing().when(sightingRepository).deleteById(birdId);
        sightingRepository.deleteById(birdId);
        // Verify behavior
        verify(sightingRepository).deleteById(birdId);
    }
}
