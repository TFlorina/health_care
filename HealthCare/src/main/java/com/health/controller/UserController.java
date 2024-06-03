package com.health.controller;

import com.health.exception.BadRequestException;
import com.health.exception.NotFoundException;
import com.health.dto.UserDTO;
import com.health.dto.responses.UserDTOResponse;
import com.health.enums.Role;
import com.health.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util. List;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserDTOResponse> addUser(@Valid @RequestBody UserDTO userDTO) throws BadRequestException {
        UserDTOResponse userDTOResponse = userService.addUser(userDTO);
        return new ResponseEntity<>(userDTOResponse, HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<UserDTOResponse> loginUser(@RequestBody UserDTO userDTO) throws BadRequestException, NotFoundException {
        UserDTOResponse userDTOResponse = userService.loginUser(userDTO);
        return new ResponseEntity<>(userDTOResponse, HttpStatus.OK);
    }
    @GetMapping("/{userId}")
    public ResponseEntity<UserDTOResponse> getUser(@PathVariable Long userId) throws NotFoundException {
        UserDTOResponse userDTO = userService.getUser(userId);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }
    @GetMapping("/coaches")
    public ResponseEntity<List<UserDTOResponse>> getCoaches() throws NotFoundException {
        List<UserDTOResponse> userDTOList = userService.getCoaches();
        return new ResponseEntity<>(userDTOList, HttpStatus.OK);
    }
    @PatchMapping("/{userId}")
    public ResponseEntity<String> updateUser(@PathVariable Long userId, @Valid @RequestBody UserDTO userDTO) throws NotFoundException {
        userService.updateUser(userId, userDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) throws NotFoundException {
        userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
