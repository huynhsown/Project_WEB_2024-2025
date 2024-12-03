package com.adidark.api;

<<<<<<< .mine
=======
import com.adidark.entity.UserEntity;
>>>>>>> .theirs
import com.adidark.model.dto.UserDTO;
<<<<<<< .mine
import com.adidark.model.response.ResponseDTO;
=======
import com.adidark.model.dto.UserLoginDTO;


>>>>>>> .theirs
import com.adidark.service.RoleService;
import com.adidark.service.UserService;
<<<<<<< .mine
import com.fasterxml.jackson.core.JsonProcessingException;

=======
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
>>>>>>> .theirs
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("v1/api")
@RestController
@Slf4j
public class UserAPI {
    @Autowired
    private UserService userService;
    private RoleService roleService;

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
<<<<<<< .mine

    @DeleteMapping("/customer/delete/{id}")
    public ResponseDTO deleteUser(@PathVariable(value = "id" ,required = true) Long id){
        return userService.deleteCustomer(id);
    }

    @PostMapping("/customer/save")
    public ResponseDTO updateCustomer(@RequestPart("customer") String customerJson) throws JsonProcessingException {
        return userService.updateCustomer(customerJson);
    }










=======

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserLoginDTO userLoginDTO, BindingResult result, HttpSession session){
        try{
            if(result.hasErrors()){
                List<String> errors = result.getFieldErrors()
                        .stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .toList();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
            }

            String token = userService.login(userLoginDTO.getIdentifier(), userLoginDTO.getPassword());
            session.setAttribute("authToken", token);
            return ResponseEntity.ok(Map.of("token", token));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
>>>>>>> .theirs
}
