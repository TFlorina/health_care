package com.health.controller;

import com.health.exception.BadRequestException;
import com.health.exception.NotFoundException;
import com.health.dto.BookingDTO;
import com.health.dto.responses.BookingDTOResponseClient;
import com.health.dto.responses.BookingDTOResponseCoach;
import com.health.service.BookingService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Validated
public class BookingController {

    private final BookingService bookingService;

    @GetMapping("/client/{clientId}/bookings")
    public ResponseEntity<List<BookingDTOResponseClient>> getBookingByClient(@PathVariable Long clientId) throws NotFoundException {
        List<BookingDTOResponseClient> bookingDTOList = bookingService.getBookingForClient(clientId);
        return new ResponseEntity<>(bookingDTOList, HttpStatus.OK);
    }
    @GetMapping("/coach/{coachId}/bookings")
    public ResponseEntity<List<BookingDTOResponseCoach>> getBookingByCoach(@PathVariable Long coachId) throws NotFoundException {
        List<BookingDTOResponseCoach> bookingDTOList = bookingService.getBookingForCoach(coachId);
        return new ResponseEntity<>(bookingDTOList, HttpStatus.OK);
    }
    @PostMapping("/bookings")
    public ResponseEntity<BookingDTOResponseClient> addBooking(@RequestBody @Valid BookingDTO bookingDTO) throws NotFoundException, BadRequestException {
        BookingDTOResponseClient bookingDTOResponseClient = bookingService.addBooking(bookingDTO);
        return new ResponseEntity<>(bookingDTOResponseClient, HttpStatus.OK);
    }
    @PatchMapping("/clientId/{clientId}/bookings/{bookingId}")
    public ResponseEntity<String> updateBooking(@PathVariable Long clientId, @PathVariable Long bookingId, @Valid @RequestBody BookingDTO bookingDTO) throws NotFoundException, BadRequestException {
        bookingService.updateBooking(clientId, bookingId, bookingDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/clientId/{clientId}/bookings/{bookingId}")
    public ResponseEntity<String> deleteBooking(@PathVariable Long clientId, @PathVariable Long bookingId) throws NotFoundException {
        bookingService.deleteBooking(clientId, bookingId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
