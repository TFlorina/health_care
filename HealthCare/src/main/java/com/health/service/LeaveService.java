package com.health.service;

import com.health.exception.BadRequestException;
import com.health.exception.NotFoundException;
import com.health.dto.LeaveDTO;
import com.health.dto.responses.LeaveDTOResponse;

import java.util.List;

public interface LeaveService {
    LeaveDTOResponse applyForLeave(Long coachId, LeaveDTO leaveDTO) throws NotFoundException, BadRequestException;
    List<LeaveDTOResponse> getDaysOffForCoach(Long coachId) throws NotFoundException;
    void deleteLeave(Long coachId, Long leaveId) throws NotFoundException;
}
