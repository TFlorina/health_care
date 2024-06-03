package com.health.entity;

import com.health.enums.WeekDays;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalTime;

@Entity
@Data
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private WeekDays day;
    private LocalTime time;

    @ManyToOne
    @JoinColumn(name = "coach_id")
    private User user;
}
