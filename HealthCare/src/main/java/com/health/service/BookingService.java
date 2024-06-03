package com.health.service;

import com.health.exception.BadRequestException;
import com.health.exception.NotFoundException;
import com.health.dto.BookingDTO;
import com.health.dto.responses.BookingDTOResponseClient;
import com.health.dto.responses.BookingDTOResponseCoach;
import com.health.entity.Booking;

import java.util.List;

public interface BookingService {
    Booking getBookingById(Long id) throws NotFoundException;
    List<BookingDTOResponseClient> getBookingForClient(Long id) throws NotFoundException;
    List<BookingDTOResponseCoach> getBookingForCoach(Long id) throws NotFoundException;
    BookingDTOResponseClient addBooking(BookingDTO bookingDTO) throws NotFoundException, BadRequestException;
    void updateBooking(Long clientId, Long bookingId, BookingDTO bookingDTO) throws NotFoundException, BadRequestException;
    void deleteBooking(Long clientId, Long bookingId) throws NotFoundException;
}
