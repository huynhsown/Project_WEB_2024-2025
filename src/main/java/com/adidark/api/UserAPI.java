package com.adidark.api;

import com.adidark.entity.UserEntity;
import com.adidark.model.dto.UserDTO;
import com.adidark.model.dto.UserLoginDTO;
import com.adidark.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("v1/api")
@RestController
@Slf4j
public class UserAPI {
    @Autowired
    private UserService userService;

    @GetMapping("/search-suggestions-customer")
    public List<UserDTO> searchSuggestionsCustomer(String query){
        return userService.searchUser(query);
    }

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserDTO userDTO, BindingResult result){
        try {
            if(result.hasErrors()){
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            if(!userDTO.getPassword().equals(userDTO.getConfirmPassword())){
                return ResponseEntity.badRequest().body("Password not match");
            }
            UserEntity user = userService.createUser(userDTO);
            return ResponseEntity.ok("");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserLoginDTO userLoginDTO, BindingResult result){
        try{
            if(result.hasErrors()){
                List<String> errors = result.getFieldErrors()
                        .stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .toList();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
            }

            String token = userService.login(userLoginDTO.getIdentifier(), userLoginDTO.getPassword());
            return ResponseEntity.ok(token);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
