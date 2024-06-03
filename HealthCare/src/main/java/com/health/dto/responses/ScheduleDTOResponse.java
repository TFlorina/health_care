package com.health.dto.responses;

import com.health.enums.WeekDays;
import lombok.Data;

import java.time.LocalTime;

@Data
public class ScheduleDTOResponse {

    private Long id;
    private WeekDays day;
    private LocalTime time;
}

