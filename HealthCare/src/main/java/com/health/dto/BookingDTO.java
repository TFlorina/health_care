package com.health.dto;

import com.health.entity.User;
import com.health.validation.ValidFixedHours;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class BookingDTO {

    @ValidFixedHours(message = "{booking.time.invalid}")
    private LocalTime time;
    @FutureOrPresent
    private LocalDate date;
    private Long coach;
    private Long client;
}
