package com.health.service.impl;

import com.health.exception.NotFoundException;
import com.health.dto.ScheduleDTO;
import com.health.dto.responses.ScheduleDTOResponse;
import com.health.entity.Schedule;
import com.health.entity.User;
import com.health.repository.ScheduleRepository;
import com.health.service.ScheduleService;
import com.health.service.UserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Override
    public ScheduleDTOResponse addSchedule(Long id, ScheduleDTO scheduleDTO) throws NotFoundException {
        User user = userService.getUserById(id);
        Schedule schedule = modelMapper.map(scheduleDTO, Schedule.class);
        addScheduleToCoach(user, schedule);
        scheduleRepository.save(schedule);

        return modelMapper.map(schedule, ScheduleDTOResponse.class);
    }

    @Override
    public Schedule getScheduleById(Long id) throws NotFoundException {
        Optional<Schedule> scheduleOptional = scheduleRepository.findById(id);
        Schedule schedule = scheduleOptional.orElseThrow(() -> new NotFoundException("Schedule not found!"));

        return schedule;
    }

    @Override
    public List<ScheduleDTOResponse> getScheduleByCoach(Long id) throws NotFoundException {
        User user = userService.getUserById(id);
        List<Schedule> scheduleList = user.getScheduleList();
        return scheduleList.stream()
                            .map(schedule -> modelMapper.map(schedule, ScheduleDTOResponse.class))
                            .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void updateSchedule(Long coachId, Long scheduleId, ScheduleDTO scheduleDTO) throws NotFoundException {
        User user = userService.getUserById(coachId);

        for (Schedule s : user.getScheduleList()) {
            if (s.getId() == scheduleId) {
                if (scheduleDTO.getDay() != null) s.setDay(scheduleDTO.getDay());
                if (scheduleDTO.getTime() != null) s.setTime(scheduleDTO.getTime());
            }
        }
    }

    @Override
    public void deleteSchedule(Long coachId, Long scheduleId) throws NotFoundException {
        User user = userService.getUserById(coachId);

        for (Schedule s : user.getScheduleList()) {
            if (s.getId() == scheduleId) {
                scheduleRepository.delete(s);
            }
        }

    }

    private void addScheduleToCoach(User user, Schedule schedule) {
        user.addScheduleToCoach(schedule);
    }
}
