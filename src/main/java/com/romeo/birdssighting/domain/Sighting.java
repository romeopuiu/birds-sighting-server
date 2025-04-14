package com.romeo.birdssighting.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.CascadeType;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import java.time.LocalDateTime;


/**
 *
 This class represents a data model for a sighting entity in a database
 */
@Getter
@Setter
@Entity
@Table(name = "sighting")
public class Sighting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="bird_id", nullable=false)
    private Bird bird;

    @Column(name = "location")
    private String location;

    @JsonFormat(pattern = "YYYY-MM-dd HH:mm")
    @Column(name = "date_time")
    private LocalDateTime dateTime;
}
