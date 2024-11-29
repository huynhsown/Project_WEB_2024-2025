package com.adidark.service;

import com.adidark.entity.ProductEntity;
import com.adidark.model.dto.ProductDTO;
import com.adidark.model.dto.SuperClassDTO;
import com.adidark.model.response.ResponseDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    SuperClassDTO<ProductDTO> searchProducts(String query, Pageable pageable);
    List<ProductDTO> getSuggestions(String query);
    ResponseDTO save(String productJSON, MultipartFile[] images) throws JsonProcessingException;
    ProductDTO findProductById(Long id);
    ResponseDTO deleteById(Long id);

    // -------------- Start Phuc --------------------
    Page<ProductEntity> findAll(Pageable pageable);
    Optional<ProductDTO> findById(Long id);
    Page<ProductEntity> findByNameContainingIgnoreCase(String namePattern, Pageable pageable);
    Page<ProductEntity> filterByMultipleCriteria(String namePattern,
                                                 List<Long> supplierIds,
                                                 List<Long> colorIds,
                                                 List<Long> sizeIds,
                                                 Pageable pageable);

    // -------------- End Phuc ----------------------
}
