package com.adidark.service;

import com.adidark.entity.CartEntity;
import com.adidark.model.dto.CartDTO;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface CartService {

    CartDTO findByUserId(Long userId);
}
