package com.adidark.service;

import com.adidark.model.dto.ProductDTO;
import com.adidark.model.dto.SuperClassDTO;
import com.adidark.model.dto.UserDTO;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface UserService {
    List<UserDTO> findAll(Pageable pageable);
    SuperClassDTO<UserDTO> searchUser(String query, Pageable pageable);
}
