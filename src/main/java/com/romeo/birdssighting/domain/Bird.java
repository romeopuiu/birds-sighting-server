package com.romeo.birdssighting.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import java.util.List;


/**
 *
 This class represents a data model for a bird entity in a database
 */
@Entity
@Table(name = "bird")
@Getter
@Setter
public class Bird {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ToString.Exclude
    @OneToMany(mappedBy = "bird", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Sighting> sightings;

    @Column(name = "color")
    private String color;

    @Column(name = "weight")
    private int weight;

    @Column(name = "height")
    private int height;
}
