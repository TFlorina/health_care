package com.health.controller;

import com.health.exception.BadRequestException;
import com.health.exception.NotFoundException;
import com.health.dto.LeaveDTO;
import com.health.dto.responses.LeaveDTOResponse;
import com.health.service.LeaveService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/coaches")
@AllArgsConstructor
public class LeaveController {

    private final LeaveService leaveService;
    @PostMapping("/{coachId}/leaves")
    public ResponseEntity<LeaveDTOResponse> applyForLeave(@PathVariable Long coachId, @RequestBody LeaveDTO leaveDTO) throws NotFoundException, BadRequestException {
        LeaveDTOResponse leaveDTOResponse = leaveService.applyForLeave(coachId, leaveDTO);
        return new ResponseEntity<>(leaveDTOResponse, HttpStatus.CREATED);
    }
    @GetMapping("/{coachId}/leaves")
    public ResponseEntity<List<LeaveDTOResponse>> getDaysOffForCoach(@PathVariable Long coachId) throws NotFoundException {
        List<LeaveDTOResponse> list = leaveService.getDaysOffForCoach(coachId);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    @DeleteMapping("/{coachId}/leaves/{leaveId}")
    public ResponseEntity<String> deleteLeave(@PathVariable Long coachId, @PathVariable Long leaveId) throws NotFoundException {
        leaveService.deleteLeave(coachId, leaveId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
