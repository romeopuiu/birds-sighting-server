package com.romeo.birdssighting.mapper;

import com.romeo.birdssighting.domain.Bird;
import com.romeo.birdssighting.domain.Sighting;
import com.romeo.birdssighting.dto.BirdDTO;
import com.romeo.birdssighting.dto.SightingDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 *  This class serves as a mapper between Bird entity objects and BirdDTO
 *  data transfer objects, providing methods for converting between the two representations
 */

@Component
@RequiredArgsConstructor
public class BirdMapper extends BaseMapper<Bird, BirdDTO>{

    private final SightingMapper sightingMapper;

    // Method to convert a BirdDTO to a Bird entity
    @Override
    public Bird convertCollectionToEntity(BirdDTO dto, Object... args) {
        var bird = new Bird();
        // Copies properties from DTO to entity using Spring's BeanUtils
        if (dto != null) {
            BeanUtils.copyProperties(dto, bird);
        }
        // Convert and set sightings if available
        if (dto != null && dto.getSightings() != null) {
            var sightings = (List<Sighting>) sightingMapper.convertCollectionToEntity(dto.getSightings());
            bird.setSightings(sightings);
        }

        return bird;
    }
    // Method to convert a Bird entity to a BirdDTO
    @Override
    public BirdDTO convertToDTO(Bird entity, Object... args) {
        var birdDTO = new BirdDTO();
        // Copies properties from entity to DTO using Spring's BeanUtils
        if (entity != null) {
            BeanUtils.copyProperties(entity, birdDTO);
        }

        if (entity != null && entity.getSightings() != null) {
            var sightings = (List<SightingDTO>) sightingMapper.convertToDTO(entity.getSightings());
            birdDTO.setSightings(sightings);
        }

        return birdDTO;
    }
}
