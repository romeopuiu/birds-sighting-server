package com.romeo.birdssighting.services;


import com.romeo.birdssighting.domain.Bird;
import com.romeo.birdssighting.domain.Sighting;
import com.romeo.birdssighting.dto.BirdDTO;
import com.romeo.birdssighting.dto.SightingDTO;
import com.romeo.birdssighting.exception.ResourceNotFoundException;
import com.romeo.birdssighting.mapper.BirdMapper;
import com.romeo.birdssighting.mapper.SightingMapper;
import com.romeo.birdssighting.repositories.BirdRepository;
import com.romeo.birdssighting.repositories.SightingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;


/**
 *
 * This class represents a service component responsible
 * for managing business logic related to birds
 */

@Transactional
@Service
@RequiredArgsConstructor
public class BirdService {

    private final BirdRepository birdRepository;
    private final BirdMapper birdMapper;
    private final SightingRepository iSightingRepository;
    private final SightingMapper sightingMapper;

    /**
     * This method is used for save a bird
     */
    public BirdDTO saveBird(BirdDTO birdDTO) {
        var bird = birdMapper.convertCollectionToEntity(birdDTO);
        bird = birdRepository.save(bird);
        var sightingDTOS = birdDTO.getSightings();

        if (sightingDTOS != null && !sightingDTOS.isEmpty()) {
            List<Sighting> sightings = (List<Sighting>) sightingMapper.convertCollectionToEntity(sightingDTOS);
            for (Sighting sighting : sightings) {
                sighting.setBird(bird);
            }
            iSightingRepository.saveAll(sightings);
            bird.setSightings(sightings);
        }

        return birdMapper.convertToDTO(bird);
    }

    /**
     * This method is used for update a bird.
     */
    public BirdDTO updateBird(Long id, BirdDTO birdDTO) {
        var bird = birdRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bird not exist with id :" + id));

        bird.setName(birdDTO.getName());
        bird.setColor(birdDTO.getColor());
        bird.setHeight(birdDTO.getHeight());
        bird.setWeight(birdDTO.getWeight());


       // bird.getSightings().clear();
        List<Sighting> newSightings = (List<Sighting>) sightingMapper.convertCollectionToEntity(birdDTO.getSightings());
        newSightings.forEach(s -> s.setBird(bird));
        bird.getSightings().addAll(newSightings);

        birdRepository.save(bird);
        return birdMapper.convertToDTO(bird);
    }
  /*  public BirdDTO updateBird(Long id, BirdDTO birdDTO) {
        var bird = birdRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bird not exist with id :" + id));

        bird.setName(birdDTO.getName());
        bird.setColor(birdDTO.getColor());
        bird.setHeight(birdDTO.getHeight());
        bird.setWeight(birdDTO.getWeight());

        bird.getSightings().clear();

        List<Sighting> sightings = (List<Sighting>) sightingMapper.convertCollectionToEntity(birdDTO.getSightings());
        sightings.forEach(sighting -> {
            sighting.setBird(bird);
            bird.getSightings().add(sighting);
        });

        birdRepository.save(bird); // Cascades and handles orphan removal

        return birdMapper.convertToDTO(bird);
    }*/


    /**
     * This method is used for returns all birds.
     */
    public List<BirdDTO> getAllBirds() {
        var birds = birdRepository.findAll();

        List<BirdDTO> birdDTOs = new ArrayList<>();
        for (Bird bird : birds) {
            var birdDTO = birdMapper.convertToDTO(bird);
            List<Sighting> sightings = iSightingRepository.findByBird(bird);
            List<SightingDTO> sightingDTOs = (List<SightingDTO>) sightingMapper.convertToDTO(sightings);
            birdDTO.setSightings(sightingDTOs);
            birdDTOs.add(birdDTO);
        }

        return birdDTOs;
    }

    /**
     * This method is used to returns a bird by name
     */
    public BirdDTO findBirdByName(String name) {
        var bird = birdRepository.findByName(name);
        if (bird == null) {
            throw new ResourceNotFoundException("Bird not found with name: " + name);
        }

        return birdMapper.convertToDTO(bird);
    }

    /**
     * This method is used to returns a bird by color
     */
    public BirdDTO findBirdByColor(String color) {
        var bird = birdRepository.findByColor(color);
        if (bird == null) {
            throw new ResourceNotFoundException("Bird not found with color: " + color);
        }
        return birdMapper.convertToDTO(bird);

    }

    /**
     * This method is used to delete a bird by id
     */
    public void deleteBird(Long id) {
        if (birdRepository.existsById(id)) {
            birdRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Bird does not exist with id: " + id);
        }
    }
}
