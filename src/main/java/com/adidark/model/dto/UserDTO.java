package com.adidark.model.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private int id;
    private String email;
    private String firstName;
    private String lastName;
    private String telephone;
    private String username;
    private String password;

}
