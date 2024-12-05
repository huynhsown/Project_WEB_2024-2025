package com.adidark.service.impl;

import com.adidark.entity.DiscountEntity;
import com.adidark.repository.DiscountRepository;
import com.adidark.service.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class DiscountServiceImpl implements DiscountService {

    @Autowired
    private DiscountRepository discountRepository;

    @Override
    public DiscountEntity save(DiscountEntity discountEntity) {
        return discountRepository.save(discountEntity);
    }

    @Override
    public void deleteById(Long discountId) {
        discountRepository.deleteById(discountId);
    }

    @Override
    public Page<DiscountEntity> searchDiscountByName(String name, Pageable pageable) {
        return discountRepository.findByNameContainingIgnoreCase(name, pageable);
    }

    @Override
    public Page<DiscountEntity> sortDiscountByPercent(boolean ascending, Pageable pageable) {
        return ascending
            ? discountRepository.findAllByOrderByDiscountPercentAsc(pageable)
            : discountRepository.findAllByOrderByDiscountPercentDesc(pageable);
    }
}
