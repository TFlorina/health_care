package com.health.dto.responses;

import com.health.dto.UserDTO;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class BookingDTOResponseCoach {

    private Long id;
    private LocalTime time;
    private LocalDate date;
    private UserDTO client;
}
