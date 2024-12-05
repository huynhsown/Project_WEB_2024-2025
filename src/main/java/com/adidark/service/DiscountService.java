package com.adidark.service;

import com.adidark.entity.DiscountEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public interface DiscountService {
    DiscountEntity save(DiscountEntity discountEntity);
    void deleteById(Long discountId);
    Page<DiscountEntity> searchDiscountByName(String name, Pageable pageable);
    Page<DiscountEntity> sortDiscountByPercent(boolean ascending, Pageable pageable);
}
