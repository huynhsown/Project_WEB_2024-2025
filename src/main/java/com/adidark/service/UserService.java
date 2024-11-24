package com.adidark.service;

import com.adidark.model.dto.ProductDTO;
import com.adidark.model.dto.SuperClassDTO;
import com.adidark.model.dto.UserDTO;

import org.springframework.data.domain.Pageable;
import java.util.List;

public interface UserService {
    List<UserDTO> findAll(Pageable pageable);
    SuperClassDTO<UserDTO> searchProducts(String query, Pageable pageable);
}
