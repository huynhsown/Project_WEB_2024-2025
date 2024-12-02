package com.adidark.api;

import com.adidark.entity.RoleEntity;
import com.adidark.model.dto.UserDTO;
import com.adidark.model.response.ResponseDTO;
import com.adidark.repository.RoleRepository;
import com.adidark.repository.UserRepository;
import com.adidark.service.RoleService;
import com.adidark.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("/user")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO){
        return ResponseEntity.ok(userService.createUser(userDTO));
    }

    @DeleteMapping("/customer/delete/{id}")
    public ResponseDTO deleteUser(@PathVariable(value = "id" ,required = true) Long id){
        return userService.deleteCustomer(id);
    }

    @PostMapping("/customer/save")
    public ResponseDTO updateCustomer(@RequestPart("customer") String customerJson) throws JsonProcessingException {
        return userService.updateCustomer(customerJson);
    }
}
