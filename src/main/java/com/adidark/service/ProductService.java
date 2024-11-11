package com.adidark.service;

import org.springframework.stereotype.Service;

@Service
public interface ProductService {
    void deleteById(Long id);
}
