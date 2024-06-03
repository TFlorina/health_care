package com.health.dto.responses;

import lombok.Data;

@Data
public class CoachDescriptionDTOResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String university;
    private Integer yearOfGraduation;
    private String speciality;
    private Integer experience;
    private Integer age;

}
