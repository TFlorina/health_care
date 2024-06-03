package com.health.service;

import com.health.exception.BadRequestException;
import com.health.exception.NotFoundException;
import com.health.dto.CoachDescriptionDTO;
import com.health.dto.responses.CoachDescriptionDTOResponse;

import java.util.List;

public interface CoachDescriptionService {

    CoachDescriptionDTOResponse addDescription(Long id, CoachDescriptionDTO coachDescriptionDTO) throws NotFoundException;
    CoachDescriptionDTOResponse getDescription(Long coachId) throws NotFoundException;
    List<CoachDescriptionDTOResponse> getDescriptions() throws NotFoundException;
    void updateDescription(Long coachId, Long descriptionId, CoachDescriptionDTO coachDescriptionDTO) throws NotFoundException, BadRequestException;
    void deleteDescription(Long coachId, Long descriptionId) throws NotFoundException;

}
