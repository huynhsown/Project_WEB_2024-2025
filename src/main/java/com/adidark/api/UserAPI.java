package com.adidark.api;

import com.adidark.model.dto.UserDTO;
import com.adidark.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("v1/api")
@RestController
@Slf4j
public class UserAPI {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/search-suggestions-customer")
    public Page<UserDTO> searchSuggestionsCustomer(String query){
        return null;
    }
}
