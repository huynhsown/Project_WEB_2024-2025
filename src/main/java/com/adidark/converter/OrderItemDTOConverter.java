package com.adidark.converter;

import com.adidark.entity.OrderItemEntity;
import com.adidark.entity.ProductSizeEntity;
import com.adidark.model.dto.OrderItemDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderItemDTOConverter {

    @Autowired
    private ProductDTOConverter productDTOConverter; // To handle nested ProductDTO conversion

    @Autowired
    private SizeDTOConverter sizeDTOConverter;

    public OrderItemDTO toOrderItemDTO(OrderItemEntity orderItemEntity) {
        if (orderItemEntity == null) {
            return null;
        }

        OrderItemDTO orderItemDTO = new OrderItemDTO(); // modelMapper.map(orderItemEntity, OrderItemDTO.class);
        orderItemDTO.setId(orderItemEntity.getId());
        orderItemDTO.setQuantity(orderItemEntity.getQuantity());
        orderItemDTO.setPrice(orderItemEntity.getPrice());
        orderItemDTO.setTotalPrice(orderItemEntity.getTotalPrice());

        // Map nested ProductEntity to ProductDTO
        if (orderItemEntity.getProductSizeEntity() != null) {
            ProductSizeEntity productSizeEntity = orderItemEntity.getProductSizeEntity();
            System.out.println("ProductSizeEntityID=" + productSizeEntity.getId());
            orderItemDTO.setProduct(productDTOConverter.toProductDTO(orderItemEntity.getProductSizeEntity().getProductEntity()));
            orderItemDTO.setSize(sizeDTOConverter.toSizeDTO(orderItemEntity.getProductSizeEntity().getSizeEntity()));
        }

        // Prevent circular reference by not setting the order field in the DTO
        orderItemDTO.setOrderId(orderItemEntity.getOrderEntity().getId());
        orderItemDTO.setProductSizeId(orderItemEntity.getProductSizeEntity().getId());

        return orderItemDTO;
    }
}
