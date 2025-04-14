package com.romeo.birdssighting.repositories;

import com.romeo.birdssighting.domain.Bird;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface for managing persistence operations related to the Bird entity
 */

public interface BirdRepository extends JpaRepository<Bird, Long> {

    /**
     * This method is used to find bird by name
     */
    Bird findByName(String name);

    /**
     * This method is used to find bird by color
     */
    Bird findByColor(String color);

    /**
     * This method is used to check bird by name
     */
    boolean existsByName(String name);
}
