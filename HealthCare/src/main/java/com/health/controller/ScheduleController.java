package com.health.controller;

import com.health.exception.NotFoundException;
import com.health.dto.ScheduleDTO;
import com.health.dto.responses.ScheduleDTOResponse;
import com.health.service.ScheduleService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/coaches")
@AllArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping("/{coachId}/schedules")
    public ResponseEntity<ScheduleDTOResponse> addSchedule(@PathVariable Long coachId, @Valid @RequestBody ScheduleDTO scheduleDTO) throws NotFoundException {
        ScheduleDTOResponse scheduleDTOResponse = scheduleService.addSchedule(coachId, scheduleDTO);
        return new ResponseEntity<>(scheduleDTOResponse, HttpStatus.CREATED);
    }
    @GetMapping("/{coachId}/schedules")
    public ResponseEntity<List<ScheduleDTOResponse>> getScheduleByCoach(@PathVariable Long coachId) throws NotFoundException {
        List<ScheduleDTOResponse> scheduleDTOS = scheduleService.getScheduleByCoach(coachId);
        return new ResponseEntity<>(scheduleDTOS, HttpStatus.OK);
    }
    @PatchMapping("/{coachId}/schedules/{scheduleId}")
    public ResponseEntity<String> updateSchedule(@PathVariable Long coachId, @PathVariable Long scheduleId, @Valid @RequestBody ScheduleDTO scheduleDTO) throws NotFoundException {
        scheduleService.updateSchedule(coachId, scheduleId, scheduleDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @DeleteMapping("/{coachId}/schedules/{scheduleId}")
    public ResponseEntity<String> deleteSchedule(@PathVariable Long coachId, @PathVariable Long scheduleId) throws NotFoundException {
        scheduleService.deleteSchedule(coachId, scheduleId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
