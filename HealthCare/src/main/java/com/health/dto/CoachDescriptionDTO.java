package com.health.dto;

import com.health.validation.ValidYear;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NonNull;

import javax.xml.transform.Source;

@Data
public class CoachDescriptionDTO{

    @Pattern(regexp = "^\\w+( \\w+)*$", message = "{description.university.invalid}")
    private String university;
    @ValidYear(message = "{description.yearOfGraduation.invalid}")
    private Integer yearOfGraduation;
    @Pattern(regexp = "^\\w+( \\w+)*$", message = "{description.speciality.invalid}")
    private String speciality;
    @Min(value = 1, message = "{description.experience.invalid}")
    private Integer experience;
    @Min(value = 18, message = "{description.age.invalid}")
    private Integer age;

}
