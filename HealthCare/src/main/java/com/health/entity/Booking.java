package com.health.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalTime time;
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "coach_id")
    private User coach;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private User client;

}
