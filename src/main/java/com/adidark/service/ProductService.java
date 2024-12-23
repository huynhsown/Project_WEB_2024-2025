package com.adidark.service;

import com.adidark.entity.ProductEntity;
import com.adidark.model.dto.ProductDTO;
import com.adidark.model.dto.SuperClassDTO;
import com.adidark.model.response.ResponseDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<ProductDTO> findAllProducts(Pageable pageable);
    SuperClassDTO<ProductDTO> searchProducts(String query, Pageable pageable);
    List<ProductDTO> getSuggestions(String query);
    ResponseDTO save(String productJSON, MultipartFile[] images) throws JsonProcessingException;


    Page<ProductEntity> findAll(Pageable pageable);
    Page<ProductEntity> findByNameContainingIgnoreCase(String namePattern, Pageable pageable);
    Page<ProductEntity> filterByMultipleCriteria(String namePattern,
                                                 List<Long> supplierIds,
                                                 List<Long> colorIds,
                                                 List<Long> sizeIds,
                                                 Pageable pageable);
    Page<ProductEntity> filterByMultipleCriteria2(String namePattern,
                                                 Pageable pageable);

    Page<ProductEntity> filterByMultipleCriteria3(List<Long> supplierIds,
                                                  Pageable pageable);

    Page<ProductEntity> filterByMultipleCriteria4(List<Long> sizeIds,
                                                  Pageable pageable);

    Page<ProductEntity> filterByMultipleCriteria5(List<Long> colorIds,
                                                  Pageable pageable);
    Optional<ProductEntity> findById(Long id);
}
