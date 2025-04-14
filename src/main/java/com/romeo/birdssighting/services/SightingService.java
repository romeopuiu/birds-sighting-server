package com.romeo.birdssighting.services;


import com.romeo.birdssighting.domain.Sighting;
import com.romeo.birdssighting.dto.SightingDTO;
import com.romeo.birdssighting.exception.ResourceNotFoundException;
import com.romeo.birdssighting.mapper.BirdMapper;
import com.romeo.birdssighting.mapper.SightingMapper;
import com.romeo.birdssighting.repositories.BirdRepository;
import com.romeo.birdssighting.repositories.SightingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * This class represents a service component responsible
 * for managing business logic related to sightings
 */

@Transactional
@Service
@RequiredArgsConstructor
@Slf4j
public class SightingService {

    private final SightingRepository sightingRepository;
    private final SightingMapper sightingMapper;
    private final BirdRepository iBirdRepository;
    private final BirdMapper birdMapper;

    /**
     * This method is used to save a sighting
     */
    public SightingDTO createSighting(Long birdId, SightingDTO sightingDTO) {
        log.info("Save a SightingDTO: {}", sightingDTO);
        var bird = iBirdRepository.findById(birdId)
                .orElseThrow(() -> new EntityNotFoundException("Bird with birdId not found" + birdId));
        var sighting = sightingMapper.convertCollectionToEntity(sightingDTO);
        sighting.setBird(bird);
        sighting = sightingRepository.save(sighting);
        var savedSightingDTO = sightingMapper.convertToDTO(sighting);
        savedSightingDTO.setBird(birdMapper.convertToDTO(bird));

        return savedSightingDTO;
    }

    /**
     * This method is used to update sighting.
     */
    public SightingDTO updateSighting(Long id, SightingDTO sightingDTO) {
        log.info("Update a SightingDTO: {}", sightingDTO);
        var sighting = sightingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sighting does not exist with id :" + id));

        sighting.setLocation(sightingDTO.getLocation());
        sighting.setDateTime(sightingDTO.getDateTime());
        sightingRepository.save(sighting);
        sightingDTO = sightingMapper.convertToDTO(sighting);
        var birdDTO = birdMapper.convertToDTO(sighting.getBird());
        sightingDTO.setBird(birdDTO);

        return sightingDTO;
    }

    /**
     * This method is used to returns all sightings by location.
     */
    public List<SightingDTO> findByLocation(String location) {
        log.info("Get all sightings by location: {}", location);
        var birds = sightingRepository.findByLocation(location);
        return new ArrayList<>(sightingMapper.convertToDTO(birds));
    }

    /**
     * This method is used to returns all sightings by date time.
     */
    public List<SightingDTO> findByDateTime(LocalDateTime dateTime) {
        log.info("Get all sightings by dateTime: {}", dateTime);
        var birds = sightingRepository.findByDateTime(dateTime);
        return new ArrayList<>(sightingMapper.convertToDTO(birds));
    }

    /**
     * This method is used to returns all sightings allocated to id of bird.
     */
    public List<SightingDTO> findAllSightingsByBirdId(Long birdId) {
        if (!iBirdRepository.existsById(birdId)) {
           throw new ResourceNotFoundException("Bird does not exist with id: " + birdId);
        }

        var sightings = sightingRepository.findByBirdId(birdId);
        return new ArrayList<>(sightingMapper.convertToDTO(sightings));
    }

    /**
     * This method is used to returns all sightings allocated to id of bird.
     */
    public List<SightingDTO> findAllSightingsByBirdName(String name) {
        if (!iBirdRepository.existsByName(name)) {
            throw new ResourceNotFoundException("Bird does not exist with name: " + name);
        }

        List<Sighting> sightings = sightingRepository.findByBirdName(name);

        if (sightings.isEmpty()) {
            return new ArrayList<>();
        }

        return new ArrayList<>(sightingMapper.convertToDTO(sightings));
    }


    /**
     * This method is used to returns all sightings.
     */
    public List<SightingDTO> findAllSightings() {
       var sightings = sightingRepository.findAll();

        return getAllSightings(sightings);
    }

    private List<SightingDTO> getAllSightings(List<Sighting> sightings) {
        List<SightingDTO> sightingDTOs = new ArrayList<>();

        for (Sighting sighting : sightings) {
            var sightingDTO = sightingMapper.convertToDTO(sighting);
            var birdDTO = birdMapper.convertToDTO(sighting.getBird());
            sightingDTO.setBird(birdDTO);
            sightingDTOs.add(sightingDTO);
        }

        return sightingDTOs;
    }

    /**
     * This method is used to returns a SightingDTO object by id.
     */
    public SightingDTO findSighting(Long id) {
        var sighting = sightingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sighting does not exist with id:" + id));

        return sightingMapper.convertToDTO(sighting);
    }

    /**
     * This method is used to delete sighting by id
     */
    public void deleteSighting(Long id) {
        if (sightingRepository.existsById(id)) {
            sightingRepository.deleteById(id);
        } else  {
            throw new ResourceNotFoundException("Sighting not exist with id :" + id);
        }
    }
}
