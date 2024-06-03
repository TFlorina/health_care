package com.health.controller;

import com.health.exception.BadRequestException;
import com.health.exception.NotFoundException;
import com.health.dto.CoachDescriptionDTO;
import com.health.dto.responses.CoachDescriptionDTOResponse;
import com.health.service.CoachDescriptionService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/coaches")
@AllArgsConstructor
public class CoachDescriptionController {

    private final CoachDescriptionService coachDescriptionService;

    @PostMapping("/{coachId}/descriptions")
    public ResponseEntity<CoachDescriptionDTOResponse> addDescription(@PathVariable Long coachId, @Valid @RequestBody CoachDescriptionDTO coachDescriptionDTO) throws NotFoundException {
        CoachDescriptionDTOResponse coachDescriptionDTOResponse = coachDescriptionService.addDescription(coachId, coachDescriptionDTO);
        return new ResponseEntity<>(coachDescriptionDTOResponse, HttpStatus.CREATED);
    }
    @GetMapping("/{coachId}/descriptions")
    public ResponseEntity<CoachDescriptionDTOResponse> getDescription(@PathVariable Long coachId) throws NotFoundException {
        CoachDescriptionDTOResponse coachDescriptionDTOResponse = coachDescriptionService.getDescription(coachId);
        return new ResponseEntity<>(coachDescriptionDTOResponse, HttpStatus.OK);
    }
    @GetMapping("/descriptions")
    public ResponseEntity<List<CoachDescriptionDTOResponse>> getDescriptions() throws NotFoundException {
        List<CoachDescriptionDTOResponse> coachDescriptionDTOResponses = coachDescriptionService.getDescriptions();
        return new ResponseEntity<>(coachDescriptionDTOResponses, HttpStatus.OK);
    }
    @PatchMapping("/{coachId}/descriptions/{descriptionId}")
    public ResponseEntity<String> updateDescription(@PathVariable Long coachId, @PathVariable Long descriptionId, @Valid @RequestBody CoachDescriptionDTO coachDescriptionDTO) throws NotFoundException, BadRequestException {
        coachDescriptionService.updateDescription(coachId, descriptionId, coachDescriptionDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @DeleteMapping("/{coachId}/descriptions/{descriptionId}")
    public ResponseEntity<String> deleteDescription(@PathVariable Long coachId, @PathVariable Long descriptionId) throws NotFoundException {
        coachDescriptionService.deleteDescription(coachId, descriptionId);
        return new ResponseEntity<>( HttpStatus.NO_CONTENT);
    }

}
