package com.health.service.impl;

import com.health.exception.BadRequestException;
import com.health.exception.NotFoundException;
import com.health.dto.UserDTO;
import com.health.dto.responses.UserDTOResponse;
import com.health.entity.User;
import com.health.enums.Role;
import com.health.repository.UserRepository;
import com.health.service.UserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    @Override
    public UserDTOResponse addUser(UserDTO userDTO) throws BadRequestException {
        User userByEmail = userRepository.findByEmail(userDTO.getEmail());

        if(userByEmail != null) throw new BadRequestException("This email is already registered!");

        User user = modelMapper.map(userDTO, User.class);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);

        return modelMapper.map(user, UserDTOResponse.class);
    }

    @Override
    public UserDTOResponse loginUser(UserDTO userDTO) throws BadRequestException, NotFoundException {
        if(userDTO.getEmail() == null || userDTO.getEmail().isEmpty()
        || userDTO.getPassword() == null || userDTO.getPassword().isEmpty()) {
            throw new BadRequestException("Please provide credentials!");
        }
        User user = userRepository.findByEmail(userDTO.getEmail());

        if(user == null) throw new NotFoundException("The email is incorrect");
        if(!passwordEncoder.matches(userDTO.getPassword(), user.getPassword())) {
            throw new BadRequestException("The password is incorrect!");
        }
        return modelMapper.map(user, UserDTOResponse.class);
    }
    @Override
    public UserDTOResponse getUser(Long id) throws NotFoundException {

        User user = getUserById(id);

        return modelMapper.map(user, UserDTOResponse.class);
    }
    @Override
    public Role getRole(Long id) throws NotFoundException {
        return getUserById(id).getRole();
    }
    @Override
    public List<UserDTOResponse> getCoaches() {

        List<User> userList = userRepository.findAllCoaches();
        return userList.stream()
                       .map(user -> modelMapper.map(user, UserDTOResponse.class))
                       .collect(Collectors.toList());

    }
    @Override
    @Transactional
    public void updateUser(Long id, UserDTO userDTO) throws NotFoundException {
        User user = getUserById(id);

        if(userDTO.getFirstName() != null) user.setFirstName(userDTO.getFirstName());
        if(userDTO.getLastName() != null) user.setLastName(userDTO.getLastName());
        if(userDTO.getEmail() != null)  user.setEmail(userDTO.getEmail());
        if(userDTO.getCity() != null) user.setCity(userDTO.getCity());

        user.setUpdatedAt(LocalDateTime.now());
    }
    @Override
    public void deleteUser(Long id) throws NotFoundException {
        User user = getUserById(id);
        userRepository.delete(user);
    }
    @Override
    public User getUserById(Long id) throws NotFoundException {
        Optional<User> userOptional = userRepository.findById(id);
        User user = userOptional.orElseThrow(() -> new NotFoundException("This user doesn't exist!"));

        return user;
    }
}
