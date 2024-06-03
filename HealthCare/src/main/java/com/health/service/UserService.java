package com.health.service;

import com.health.exception.BadRequestException;
import com.health.exception.NotFoundException;
import com.health.dto.UserDTO;
import com.health.dto.responses.UserDTOResponse;
import com.health.entity.User;
import com.health.enums.Role;

import java.util.List;

public interface UserService{
    UserDTOResponse addUser(UserDTO userDTO) throws BadRequestException;
    UserDTOResponse loginUser(UserDTO userDTO) throws BadRequestException, NotFoundException;
    UserDTOResponse getUser(Long id) throws NotFoundException;
    User getUserById(Long id) throws NotFoundException;
    List<UserDTOResponse> getCoaches();
    Role getRole(Long id) throws NotFoundException;
    void updateUser(Long id, UserDTO userDTO) throws NotFoundException;
    void deleteUser(Long id) throws NotFoundException;
}
