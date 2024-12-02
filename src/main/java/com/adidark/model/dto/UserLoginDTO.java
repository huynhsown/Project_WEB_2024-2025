package com.adidark.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginDTO {

    @NotBlank(message = "Username/Phone/Email is required")
    private String identifier;

    @NotBlank(message = "Password is required")
    private String password;
}
