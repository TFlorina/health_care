package com.health.dto;

import com.health.enums.LeaveType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

import java.time.LocalDate;

@Data
public class LeaveDTO {

    @FutureOrPresent
    private LocalDate startDate;
    @FutureOrPresent
    private LocalDate endDate;
    @Enumerated(value = EnumType.STRING)
    private LeaveType type;

}
