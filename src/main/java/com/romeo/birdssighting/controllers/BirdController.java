package com.romeo.birdssighting.controllers;


import com.romeo.birdssighting.dto.BirdDTO;
import com.romeo.birdssighting.services.BirdService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;

import static com.romeo.birdssighting.controllers.BirdController.PATH;

/**
 * This class represents a REST controller for managing
 * bird-related operations within an API.
 * This class serves as a controller component responsible for
 * handling HTTP requests related to bird resources
 */

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "Bird Controller", description = "APIs for managing birds")
@RequestMapping(PATH)
public class BirdController {

    public static final String PATH = "/api/birds";
    private final BirdService birdService;

    /**
     * This method is used for returns all birds
     */
    @Operation(summary = "Get all birds", description = "Returns a list of all birds in the system.")
    @ApiResponse(responseCode = "200", description = "List of birds returned successfully")
    @GetMapping
    public ResponseEntity<List<BirdDTO>> getAllBirds() {
        return ResponseEntity.ok(birdService.getAllBirds());
    }

    /**
     * This method is used for save a bird
     */
    @Operation(summary = "Save a bird", description = "Saves a new bird into the system.")
    @ApiResponse(responseCode = "200", description = "Bird saved successfully")
    @PostMapping("/save")
    public ResponseEntity<BirdDTO> saveBird(@RequestBody BirdDTO birdDTO) {
        log.info("REST request to save birdDTO: {}", birdDTO);
        return ResponseEntity.ok(birdService.saveBird(birdDTO));
    }

    /**
     * This method is used for returns a bird by color
     */
    @Operation(summary = "Find a bird by color", description = "Returns a bird that matches the given color.")
    @ApiResponse(responseCode = "200", description = "Bird found successfully")
    @GetMapping("/color")
    public ResponseEntity<BirdDTO> findByColor(@RequestParam String color) {
        log.info("REST request to get a bird by color: {}", color);
        return ResponseEntity.ok(birdService.findBirdByColor(color));
    }

    /**
     *  This method is used for returns a bird by name
     */
    @Operation(summary = "Find a bird by name", description = "Returns a bird that matches the given name.")
    @ApiResponse(responseCode = "200", description = "Bird found successfully")
    @GetMapping("/name")
    public ResponseEntity<BirdDTO> findByName(@RequestParam String name) {
        log.info("REST request to get a bird by name: {}", name);
        return ResponseEntity.ok(birdService.findBirdByName(name));
    }

    /**
     * This method is used for update a bird
     */
    @Operation(summary = "Update a bird", description = "Updates an existing bird by ID.")
    @ApiResponse(responseCode = "200", description = "Bird updated successfully")
    @PutMapping("/{id}")
    public ResponseEntity<BirdDTO> updateBird(@PathVariable("id") Long id, @RequestBody BirdDTO birdDTO) {
        log.info("REST request to update birdDTO: {}", birdDTO);
        return ResponseEntity.ok(birdService.updateBird(id, birdDTO));
    }

    /**
     * This method is used for delete a bird by id
     */
    @Operation(summary = "Delete a bird", description = "Deletes a bird by its ID.")
    @ApiResponse(responseCode = "200", description = "Bird deleted successfully")
    @DeleteMapping("/{id}")
    public void deleteBird(@PathVariable Long id) {
        log.info("REST request to delete bird : {}", id);
        birdService.deleteBird(id);
    }
}
