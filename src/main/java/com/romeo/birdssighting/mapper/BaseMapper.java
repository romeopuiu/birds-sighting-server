package com.romeo.birdssighting.mapper;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 *  This class provides a flexible and reusable foundation for mapping
 *  between entity objects and DTO
 *
 */

public abstract class BaseMapper<E, D> {

    // Method to convert a DTO (Data Transfer Object) to an entity
    public abstract E convertCollectionToEntity(D dto, Object... args);

    // Method to convert an entity to a DTO (Data Transfer Object)
    public abstract D convertToDTO(E entity, Object... args);

    // Method to convert a collection of DTOs to a collection of entities
    public Collection<E> convertCollectionToEntity(Collection<D> dto, Object... args) {
        // Utilizes Java Streams to map each DTO to its corresponding entity
        return dto.stream().map(d -> convertCollectionToEntity(d, args)).collect(Collectors.toList());
    }

    // Method to convert a collection of DTOs to a collection of entities
    public Collection<D> convertToDTO(Collection<E> entity, Object... args) {
        // Utilizes Java Streams to map each entity to its corresponding DTO
        return entity.stream().map(e -> convertToDTO(e, args)).collect(Collectors.toList());
    }
}
