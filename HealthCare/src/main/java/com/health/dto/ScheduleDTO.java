package com.health.dto;

import com.health.enums.WeekDays;
import com.health.validation.ValidFixedHours;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalTime;

@Data
public class ScheduleDTO {

    @Enumerated(EnumType.STRING)
    private WeekDays day;
    @ValidFixedHours(message = "{schedule.time.invalid}")
    private LocalTime time;
}
