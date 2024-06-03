package com.health.service.impl;

import com.health.exception.BadRequestException;
import com.health.exception.NotFoundException;
import com.health.dto.LeaveDTO;
import com.health.dto.responses.LeaveDTOResponse;
import com.health.entity.Leave;
import com.health.entity.User;
import com.health.repository.LeaveRepository;
import com.health.service.LeaveService;
import com.health.service.UserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class LeaveServiceImpl implements LeaveService {

    private final LeaveRepository leaveRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Override
    public LeaveDTOResponse applyForLeave(Long coachId, LeaveDTO leaveDTO) throws NotFoundException, BadRequestException {

        User coach = userService.getUserById(coachId);
        List<Leave> existingLeaves = coach.getLeaveRequests();
        short noOfDaysForCoach = calculateLeaveDays(leaveDTO.getStartDate(), leaveDTO.getEndDate());

        if(leaveDTO.getType().toString().equals("UNPAID")) {

            checkForOverlappingLeaves(existingLeaves, leaveDTO.getStartDate(), leaveDTO.getEndDate());

        }else if(existingLeaves.isEmpty()) {

            checkAvailableDays(coach.getCreatedAt().getMonthValue(), noOfDaysForCoach);

        } else {

            checkForOverlappingLeaves(existingLeaves, leaveDTO.getStartDate(), leaveDTO.getEndDate());
            short noOfLeaveDays = calculateTotalLeaveDays(existingLeaves);
            checkAvailableDays(coach.getCreatedAt().getMonthValue(), noOfLeaveDays + noOfDaysForCoach);

        }

        Leave leave = modelMapper.map(leaveDTO, Leave.class);
        leave.setNoOfDays(noOfDaysForCoach);
        leave.setCoach(coach);
        leaveRepository.save(leave);

        return modelMapper.map(leave, LeaveDTOResponse.class);
    }
    @Override
    public List<LeaveDTOResponse> getDaysOffForCoach(Long coachId) throws NotFoundException {
        User coach = userService.getUserById(coachId);
        List<Leave> leaveRequests = coach.getLeaveRequests();
        return leaveRequests.stream()
                            .map(leave -> modelMapper.map(leave, LeaveDTOResponse.class))
                            .toList();
    }
    @Override
    public void deleteLeave(Long coachId, Long leaveId) throws NotFoundException {
        User user = userService.getUserById(coachId);
        for(Leave l : user.getLeaveRequests()) {
            if(l.getId() == leaveId) {
                leaveRepository.delete(l);
            }
        }
    }
    private void checkForOverlappingLeaves(List<Leave> existingLeaves, LocalDate startDate, LocalDate endDate) throws BadRequestException {
        for (Leave leave : existingLeaves) {
            if (!leave.getEndDate().isBefore(startDate) && !leave.getStartDate().isAfter(endDate)) {
                throw new BadRequestException("Leave period overlaps with existing leave");
            }
        }
    }

    private short calculateLeaveDays(LocalDate startDate, LocalDate endDate) {
        short noOfDays = 0;
        LocalDate date = startDate;
        while (!date.isAfter(endDate)) {
            if (!(date.getDayOfWeek() == java.time.DayOfWeek.SATURDAY || date.getDayOfWeek() == java.time.DayOfWeek.SUNDAY)) {
                noOfDays++;
            }
            date = date.plusDays(1);
        }
        return noOfDays;
    }
    private short calculateTotalLeaveDays(List<Leave> existingLeaves) {
        int totalDaysOff = existingLeaves.stream()
                                         .filter(x -> !x.getType().toString().equals("UNPAID"))
                                         .mapToInt(Leave::getNoOfDays)
                                         .sum();
        return (short)totalDaysOff;
    }
    private void checkAvailableDays(int monthOfEmployment, int noOfDaysOff) throws BadRequestException {
        int noOfAvailableDays = (13 - monthOfEmployment) * 2;
        if(noOfDaysOff > noOfAvailableDays) throw new BadRequestException("You have exceeded the available number of days.");
    }
}
