package com.adidark.service.impl;

import com.adidark.converter.DiscountDTOConverter;
import com.adidark.entity.DiscountEntity;
import com.adidark.model.dto.DiscountDTO;
import com.adidark.repository.DiscountRepository;
import com.adidark.service.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class DiscountServiceImpl implements DiscountService {

    @Autowired
    private DiscountRepository discountRepository;

    @Autowired
    private DiscountDTOConverter discountDTOConverter;

    @Override
    public Optional<DiscountEntity> findById(Long discountId) {
        return discountRepository.findById(discountId);
    }

    // Lấy danh sách tất cả Discount
    @Override
    public Page<DiscountDTO> getAllDiscounts(Pageable pageable) {
        Page<DiscountEntity> discounts = discountRepository.findAll(pageable);
        return discountDTOConverter.toDTOPage(discounts);
    }

    // Tạo mới Discount
    @Override
    public DiscountDTO createDiscount(DiscountDTO discountDTO) {
        DiscountEntity discountEntity = new DiscountEntity();
        discountEntity.setName(discountDTO.getName());
        discountEntity.setDescription(discountDTO.getDescription());
        discountEntity.setDiscountPercent(discountDTO.getDiscountPercent());

        discountEntity = discountRepository.save(discountEntity);
        return discountDTOConverter.toDTO(discountEntity);
    }

    // Cập nhật Discount
    @Override
    public DiscountDTO updateDiscount(Long id, DiscountDTO discountDTO) {
        DiscountEntity discountEntity = discountRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Discount not found"));

        discountEntity.setName(discountDTO.getName());
        discountEntity.setDescription(discountDTO.getDescription());
        discountEntity.setDiscountPercent(discountDTO.getDiscountPercent());

        discountEntity = discountRepository.save(discountEntity);
        return discountDTOConverter.toDTO(discountEntity);
    }

    // Xóa Discount
    @Override
    public void deleteDiscount(Long id) {
        DiscountEntity discountEntity = discountRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Discount not found"));
        discountRepository.delete(discountEntity);
    }
}
