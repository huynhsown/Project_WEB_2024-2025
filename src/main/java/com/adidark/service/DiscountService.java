package com.adidark.service;

import com.adidark.entity.DiscountEntity;
import com.adidark.model.dto.DiscountDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public interface DiscountService {
    Optional<DiscountEntity> findById(Long discountId);
    Page<DiscountDTO> getAllDiscounts(Pageable pageable);
    DiscountDTO createDiscount(DiscountDTO discountDTO);
    DiscountDTO updateDiscount(Long id, DiscountDTO discountDTO);
    void deleteDiscount(Long id);
}
