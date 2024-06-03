package com.health.service.impl;

import com.health.exception.NotFoundException;
import com.health.dto.CoachDescriptionDTO;
import com.health.dto.responses.CoachDescriptionDTOResponse;
import com.health.entity.CoachDescription;
import com.health.entity.User;
import com.health.repository.CoachDescriptionRepository;
import com.health.service.CoachDescriptionService;
import com.health.service.UserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@AllArgsConstructor
public class CoachDescriptionServiceImpl implements CoachDescriptionService {

    private final CoachDescriptionRepository coachDescriptionRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;
    @Override
    public CoachDescriptionDTOResponse addDescription(Long id, CoachDescriptionDTO coachDescriptionDTO) throws NotFoundException {
        User coach = userService.getUserById(id);
        CoachDescription coachDescription = modelMapper.map(coachDescriptionDTO, CoachDescription.class);
        coachDescription.setCoach(coach);

        coachDescriptionRepository.save(coachDescription);

        return modelMapper.map(coachDescription, CoachDescriptionDTOResponse.class);
    }

    @Override
    public CoachDescriptionDTOResponse getDescription(Long coachId) throws NotFoundException {

        User user = userService.getUserById(coachId);
        CoachDescription coachDescription = user.getCoachDescription();
        var coachDescriptionResponse = new CoachDescriptionDTOResponse();

        coachDescriptionResponse.setFirstName(user.getFirstName());
        coachDescriptionResponse.setLastName(user.getLastName());
        coachDescriptionResponse.setUniversity(coachDescription.getUniversity());
        coachDescriptionResponse.setYearOfGraduation(coachDescription.getYearOfGraduation());
        coachDescriptionResponse.setSpeciality(coachDescription.getSpeciality());
        coachDescriptionResponse.setExperience(coachDescription.getExperience());
        coachDescriptionResponse.setAge(coachDescription.getAge());

        return coachDescriptionResponse;
    }

    @Override
    public List<CoachDescriptionDTOResponse> getDescriptions() throws NotFoundException {

        List<CoachDescription> coachDescriptions = coachDescriptionRepository.findAll();
        List<CoachDescriptionDTOResponse> coachDescriptionDTOResponse = new ArrayList<>();
        for(CoachDescription c : coachDescriptions) {
            User user = userService.getUserById(c.getCoach().getId());
            CoachDescriptionDTOResponse coachDescriptionDTO = new CoachDescriptionDTOResponse();

            coachDescriptionDTO.setFirstName(user.getFirstName());
            coachDescriptionDTO.setLastName(user.getLastName());
            coachDescriptionDTO.setUniversity(c.getUniversity());
            coachDescriptionDTO.setYearOfGraduation(c.getYearOfGraduation());
            coachDescriptionDTO.setSpeciality(c.getSpeciality());
            coachDescriptionDTO.setExperience(c.getExperience());
            coachDescriptionDTO.setAge(c.getAge());

            coachDescriptionDTOResponse.add(coachDescriptionDTO);
        }

        return coachDescriptionDTOResponse;
    }

    @Override
    @Transactional
    public void updateDescription(Long coachId, Long descriptionId, CoachDescriptionDTO description) throws NotFoundException {
        User user = userService.getUserById(coachId);
        if(user.getCoachDescription().getId() != descriptionId) throw new NotFoundException("Description not found!");

        if(description.getUniversity() != null) user.getCoachDescription().setUniversity(description.getUniversity());
        if(description.getYearOfGraduation() != null) user.getCoachDescription().setYearOfGraduation(description.getYearOfGraduation());
        if(description.getSpeciality() != null) user.getCoachDescription().setSpeciality(description.getSpeciality());
        if(description.getExperience() != null) user.getCoachDescription().setExperience(description.getExperience());
        if(description.getAge() != null) user.getCoachDescription().setAge(description.getAge());

    }

    @Override
    public void deleteDescription(Long coachId, Long descriptionId) throws NotFoundException {
        User user = userService.getUserById(coachId);
        if(user.getCoachDescription().getId() != descriptionId) throw new NotFoundException("Description not found!");

        coachDescriptionRepository.delete(user.getCoachDescription());
    }

}
