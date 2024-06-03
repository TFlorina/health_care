package com.health.service;

import com.health.exception.NotFoundException;
import com.health.dto.ScheduleDTO;
import com.health.dto.responses.ScheduleDTOResponse;
import com.health.entity.Schedule;

import java.util.List;

public interface ScheduleService {

    ScheduleDTOResponse addSchedule(Long id, ScheduleDTO scheduleDTO) throws NotFoundException;
    Schedule getScheduleById(Long id) throws NotFoundException;
    List<ScheduleDTOResponse> getScheduleByCoach(Long id) throws NotFoundException;
    void updateSchedule(Long coachId, Long scheduleId, ScheduleDTO scheduleDTO) throws NotFoundException;
    void deleteSchedule(Long coachId, Long scheduleId) throws NotFoundException;
}
