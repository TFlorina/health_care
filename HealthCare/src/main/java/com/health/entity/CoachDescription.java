package com.health.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class CoachDescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "coach_id")
    private User coach;

    private String university;
    private Integer yearOfGraduation;
    private String speciality;
    private Integer experience;
    private Integer age;

}
