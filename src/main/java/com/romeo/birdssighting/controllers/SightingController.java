package com.romeo.birdssighting.controllers;


import com.romeo.birdssighting.dto.SightingDTO;
import com.romeo.birdssighting.services.SightingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;


/**
 * This class defines a REST controller responsible for
 * managing sightings within an API.
 * This class serves as a controller component
 * responsible for handling HTTP requests related to sightings
 */

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "Sighting Controller", description = "APIs for managing bird sightings")
public class SightingController {

    private final SightingService sightingService;

    /**
     * This method is used for get all sightings by birdId
     */
    @Operation(summary = "Get all sightings by bird Id")
    @ApiResponse(responseCode = "200", description = "Sightings retrieved successfully")
    @GetMapping("/birds/{birdId}/sightings")
    public ResponseEntity<List<SightingDTO>> getAllSightingsByBirdId(@PathVariable(value = "birdId") Long birdId) {
        log.info("REST request to get sightings by birdId : {}", birdId);
        return ResponseEntity.ok(sightingService.findAllSightingsByBirdId(birdId));

    }

    /**
     * This method is used for save a Sighting allocated to a bird
     */
    @Operation(summary = "Save a new sighting for a bird")
    @ApiResponse(responseCode = "200", description = "Sighting saved successfully")
    @PostMapping("/birds/{birdId}/sightings/save")
    public ResponseEntity<SightingDTO> addSighting(@PathVariable(value = "birdId") Long birdId,
                                                   @RequestBody SightingDTO sightingDTO) {
        log.info("REST request to save sightingDTO: {}", sightingDTO);
        return ResponseEntity.ok(sightingService.createSighting(birdId, sightingDTO));
    }

    /**
     * This method is used for update a sighting
     */
    @Operation(summary = "Update an existing sighting")
    @ApiResponse(responseCode = "200", description = "Sighting updated successfully")
    @PutMapping("/sighting/{id}")
    public ResponseEntity<SightingDTO> updateSighting(@PathVariable("id") Long id,
                                                      @RequestBody SightingDTO sightingDTO) {
        log.info("REST request to update a sightingDTO: {}", sightingDTO);
        return ResponseEntity.ok(sightingService.updateSighting(id, sightingDTO));
    }

    /**
     * This method is used for get a single Sighting by Id
     */
    @Operation(summary = "Get a sighting by Id")
    @ApiResponse(responseCode = "200", description = "Sighting retrieved successfully")
    @GetMapping("/sightings/{id}")
    public ResponseEntity<SightingDTO> getSightingById(@PathVariable(value = "id") Long id) {
        log.info("REST request to get sighting by id : {}", id);
        return ResponseEntity.ok(sightingService.findSighting(id));
    }

    /**
     * This method is used for get all sightings by location
     */
    @Operation(summary = "Get all sightings by location")
    @ApiResponse(responseCode = "200", description = "Sightings retrieved successfully")
    @GetMapping("/sightings/location")
    public ResponseEntity<List<SightingDTO>> getSightingByLocation(@RequestParam String location) {
        log.info("REST request to get sighting by location : {}", location);
        return ResponseEntity.ok(sightingService.findByLocation(location));
    }

    /**
     * This method is used for get all sightings by dateTime
     */
    @Operation(summary = "Get all sightings by date and time")
    @ApiResponse(responseCode = "200", description = "Sightings retrieved successfully")
    @GetMapping("/sightings/date-time")
    public ResponseEntity<List<SightingDTO>> getSightingByLocation(@RequestParam("dateTime")
                                                                   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                                   LocalDateTime dateTime) {
        return ResponseEntity.ok(sightingService.findByDateTime(dateTime));
    }

    /**
     * This method is used for returns all sightings
     */
    @Operation(summary = "Get all sightings")
    @ApiResponse(responseCode = "200", description = "All sightings retrieved successfully")
    @GetMapping("/sightings")
    public ResponseEntity<List<SightingDTO>> getAllSightings() {
        log.info("REST request for returns all sightings");
        return ResponseEntity.ok(sightingService.findAllSightings());
    }

    /**
     * This method is used for delete a Sighting by id
     */
    @Operation(summary = "Delete a sighting by Id")
    @ApiResponse(responseCode = "200", description = "Sighting deleted successfully")
    @DeleteMapping("/sightings/{id}")
    public void deleteSighting(@PathVariable Long id) {
        log.info("REST request to delete Sighting : {}", id);
        sightingService.deleteSighting(id);
    }

    /**
     * This method is used for get all sightings by name of bird
     */
    @Operation(summary = "Get all sightings by bird name")
    @ApiResponse(responseCode = "200", description = "Sightings retrieved successfully")
    @GetMapping("/birds/search-by-name/sightings")
    public ResponseEntity<List<SightingDTO>> getAllSightingsByBirdName(@RequestParam(value = "birdName") String birdName) {
        log.info("REST request to get sightings by bird name : {}", birdName);
        return ResponseEntity.ok(sightingService.findAllSightingsByBirdName(birdName));
    }
}
