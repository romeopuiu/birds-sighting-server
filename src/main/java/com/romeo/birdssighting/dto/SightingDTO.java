package com.romeo.birdssighting.dto;

import lombok.Data;

import java.time.LocalDateTime;


@Data
public class SightingDTO {

    private Long id;
    private String location;
    private BirdDTO bird;
    private LocalDateTime dateTime;
}
