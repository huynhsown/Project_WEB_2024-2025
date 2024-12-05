package com.adidark.converter;

import com.adidark.entity.DiscountEntity;
import com.adidark.model.dto.DiscountDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DiscountDTOConverter {

    public DiscountDTO toDTO(DiscountEntity discountEntity) {
        DiscountDTO discountDTO = new DiscountDTO();
        discountDTO.setId(discountEntity.getId());
        discountDTO.setName(discountEntity.getName());
        discountDTO.setDiscountPercent(discountEntity.getDiscountPercent());
        discountDTO.setDescription(discountEntity.getDescription());
        return discountDTO;
    }

    public List<DiscountDTO> toDTOList(List<DiscountEntity> entities) {
        return entities.stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }

    public Page<DiscountDTO> toDTOPage(Page<DiscountEntity> entities) {
        List<DiscountDTO> dtoList = toDTOList(entities.getContent());
        return new PageImpl<>(dtoList, entities.getPageable(), entities.getTotalElements());
    }
}
