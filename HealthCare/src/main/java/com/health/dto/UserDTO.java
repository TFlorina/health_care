package com.health.dto;

import com.health.enums.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserDTO {

    @Pattern(regexp = "^[a-zA-Z]+([-''][a-zA-Z]+)*$", message = "{user.name.invalid}")
    private String firstName;
    @Pattern(regexp = "^[a-zA-Z]+([-''][a-zA-Z]+)*$", message = "{user.name.invalid}")
    private String lastName;
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", message = "{user.password.invalid}")
    private String password;
    @Email
    private String email;
    @Pattern(regexp = "^[a-zA-Z\\s\\.\\-]+$", message = "{user.city.invalid}")
    private String city;
    @Enumerated(EnumType.STRING)
    private Role role;

}
