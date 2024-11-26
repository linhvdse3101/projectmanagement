package com.management.project.requests;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@Valid
@AllArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "userName can't empty")
    private String userName;

    @NotBlank(message = "userName can't empty")
    @Min(value = 6)
    @Max(value = 255)
    private String userPassword;

    @Email(message = "email not validate")
    private String email;
    private String roleName;

}
