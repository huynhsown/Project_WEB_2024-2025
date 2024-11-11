package com.adidark.service.impl;

import com.adidark.repository.ProductRepository;
import com.adidark.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;


    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
}
