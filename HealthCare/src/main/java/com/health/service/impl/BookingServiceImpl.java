package com.health.service.impl;

import com.health.exception.BadRequestException;
import com.health.exception.NotFoundException;
import com.health.dto.*;
import com.health.dto.responses.BookingDTOResponseClient;
import com.health.dto.responses.BookingDTOResponseCoach;
import com.health.dto.responses.LeaveDTOResponse;
import com.health.dto.responses.ScheduleDTOResponse;
import com.health.entity.Booking;
import com.health.entity.User;
import com.health.enums.WeekDays;
import com.health.repository.BookingRepository;
import com.health.service.BookingService;
import com.health.service.LeaveService;
import com.health.service.ScheduleService;
import com.health.service.UserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.*;

@Service
@AllArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final ScheduleService scheduleService;
    private final UserService userService;
    private final LeaveService leaveService;
    private final ModelMapper modelMapper;

    @Override
    public Booking getBookingById(Long id) throws NotFoundException {
        Optional<Booking> bookingOptional = bookingRepository.findById(id);
        return bookingOptional.orElseThrow(() -> new NotFoundException("Booking not found!"));
    }

    @Override
    public List<BookingDTOResponseClient> getBookingForClient(Long id) throws NotFoundException {
        User client = userService.getUserById(id);
        List<Booking> bookingList = client.getClientList();
        List<BookingDTOResponseClient> bookingDTOList = new ArrayList<>();
        if (!bookingList.isEmpty()) {
            for (Booking b : bookingList) {
                BookingDTOResponseClient bookingDTO = modelMapper.map(b, BookingDTOResponseClient.class);
                bookingDTOList.add(bookingDTO);
            }
        }
        return bookingDTOList;
    }

    @Override
    public List<BookingDTOResponseCoach> getBookingForCoach(Long id) throws NotFoundException {
        User coach = userService.getUserById(id);
        List<Booking> bookingList = coach.getCoachList();
        List<BookingDTOResponseCoach> bookingDTOList = new ArrayList<>();
        if (!bookingList.isEmpty()) {
            for (Booking b : bookingList) {
                BookingDTOResponseCoach bookingDTO = modelMapper.map(b, BookingDTOResponseCoach.class);
                bookingDTOList.add(bookingDTO);
            }
        }
        return bookingDTOList;
    }

    @Override
    public BookingDTOResponseClient addBooking(BookingDTO bookingDTO) throws NotFoundException, BadRequestException {

        if (userService.getRole(bookingDTO.getClient()).toString().equals("COACH"))
            throw new BadRequestException("You are not allowed to make an appointment!");

        BookingDTOResponseClient bookingDTOResponseClient;

        if (checkOverlappingBookingsForClient(bookingDTO))
            throw new BadRequestException("Client has already a booking in that slot!");
        if (checkOverlappingBookingsForCoach(bookingDTO))
            throw new BadRequestException("Coach has already a booking in that slot!");
        if (checkCoachSchedule(bookingDTO)) {
            Booking booking = new Booking();
            booking.setDate(bookingDTO.getDate());
            booking.setTime(bookingDTO.getTime());
            booking.setClient(userService.getUserById(bookingDTO.getClient()));
            booking.setCoach(userService.getUserById(bookingDTO.getCoach()));

            bookingRepository.save(booking);
            bookingDTOResponseClient = modelMapper.map(booking, BookingDTOResponseClient.class);
        } else throw new BadRequestException("Coach is not available in this interval!");

        return bookingDTOResponseClient;
    }

    @Override
    @Transactional
    public void updateBooking(Long clientId, Long bookingId, BookingDTO bookingDTO) throws NotFoundException, BadRequestException {

        User client = userService.getUserById(clientId);

        if (client.getRole().toString().equals("COACH"))
            throw new BadRequestException("You are not allowed to change an appointment!");

        Booking booking = new Booking();

        for (Booking b : client.getClientList()) {
            if (b.getId() == bookingId) {
                booking = b;
                break;
            }
        }
        BookingDTO bookingUpdated = new BookingDTO();
        bookingUpdated.setClient(booking.getClient().getId());
        bookingUpdated.setCoach(booking.getCoach().getId());

        if (bookingDTO.getDate() != null) bookingUpdated.setDate(bookingDTO.getDate());
        if (bookingDTO.getTime() != null) bookingUpdated.setTime(bookingDTO.getTime());

        if (checkOverlappingBookingsForClient(bookingUpdated))
            throw new BadRequestException("Client has already a booking in that slot!");
        if (checkOverlappingBookingsForCoach(bookingUpdated))
            throw new BadRequestException("Coach has already a booking in that slot!");
        if (checkCoachSchedule(bookingUpdated)) {
            booking.setDate(bookingUpdated.getDate());
            booking.setTime(bookingUpdated.getTime());
        } else throw new BadRequestException("Coach is not available in this interval!");

    }

    @Override
    public void deleteBooking(Long clientId, Long bookingId) throws NotFoundException {
        User user = userService.getUserById(clientId);

        for (Booking b : user.getClientList()) {
            if (b.getId() == bookingId) {
                bookingRepository.delete(b);
                break;
            }
        }

    }

    private boolean checkCoachSchedule(BookingDTO bookingDTO) throws NotFoundException {
        List<ScheduleDTOResponse> scheduleDTOList = scheduleService.getScheduleByCoach(bookingDTO.getCoach());

        for (ScheduleDTOResponse scheduleDTO : scheduleDTOList) {
            if (checkDate(scheduleDTO.getDay(), bookingDTO.getDate().getDayOfWeek())) {
                if (checkTime(scheduleDTO.getTime(), bookingDTO.getTime())) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkOverlappingBookingsForClient(BookingDTO bookingDTO) throws NotFoundException {
        List<BookingDTOResponseClient> list = getBookingForClient(bookingDTO.getClient());
        for (BookingDTOResponseClient b : list) {
            if (b.getDate().isEqual(bookingDTO.getDate()) && b.getTime().equals(bookingDTO.getTime())) return true;
        }
        return false;
    }

    private boolean checkOverlappingBookingsForCoach(BookingDTO bookingDTO) throws NotFoundException {
        List<BookingDTOResponseCoach> list = getBookingForCoach(bookingDTO.getCoach());
        for (BookingDTOResponseCoach b : list) {
            if (b.getDate().isEqual(bookingDTO.getDate()) && b.getTime().equals(bookingDTO.getTime())) return true;
        }
        List<LeaveDTOResponse> listDaysOff = leaveService.getDaysOffForCoach(bookingDTO.getCoach());
        for (LeaveDTOResponse l : listDaysOff) {
            if (bookingDTO.getDate().isEqual(l.getStartDate())
                    || bookingDTO.getDate().isEqual(l.getEndDate())
                    || (bookingDTO.getDate().isAfter(l.getStartDate()) && bookingDTO.getDate().isBefore(l.getEndDate()))) {
                return true;
            }
        }
        return false;
    }

    private boolean checkDate(WeekDays date1, DayOfWeek date2) {
        return Objects.equals(date1.toString(), date2.toString());
    }

    private boolean checkTime(LocalTime time1, LocalTime time2) {
        return time1.equals(time2);
    }


}
