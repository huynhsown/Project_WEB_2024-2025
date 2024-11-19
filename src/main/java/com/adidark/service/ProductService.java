package com.adidark.service;

import com.adidark.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {

    Page<ProductEntity> findAll(Pageable pageable);
    Page<ProductEntity> findByNameLike(String namePattern, Pageable pageable);
}
