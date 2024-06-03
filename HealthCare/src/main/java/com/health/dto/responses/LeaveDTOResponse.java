package com.health.dto.responses;

import com.health.enums.LeaveType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class LeaveDTOResponse {

    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private LeaveType type;
    private Short noOfDays;
}
