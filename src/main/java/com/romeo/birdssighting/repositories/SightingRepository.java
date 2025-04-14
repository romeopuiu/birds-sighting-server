package com.romeo.birdssighting.repositories;

import com.romeo.birdssighting.domain.Bird;
import com.romeo.birdssighting.domain.Sighting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Interface for managing persistence operations related to the Sighting entity
 */

public interface SightingRepository extends JpaRepository<Sighting, Long> {
    /**
     * This method is used to find Sighting by birdId
     */
    List<Sighting> findByBirdId(Long birdId);

    /**
     * This method is used to find Sighting by birdId
     */
    List<Sighting> findByBirdName(String name);

    /**
     * This method is used to find Sighting by location
     */
    @Query("select s from Sighting s where s.location = :location")
    List<Sighting> findByLocation(String location);

    /**
     * This method is used to find all Sightings by dateTime
     */
    List<Sighting> findByDateTime(LocalDateTime dateTime);
    /**
     * This method is used to delete a sighting
     */
    void deleteById(Long id);

    /**
     * This method is used to return a sighting by bird
     */
    @Query("SELECT s FROM Sighting s JOIN FETCH s.bird WHERE s.bird = :bird")
    List<Sighting> findByBird(@Param("bird") Bird bird);
}
