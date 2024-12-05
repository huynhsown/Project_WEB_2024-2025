package com.adidark.converter;

import com.adidark.entity.CartItemEntity;
import com.adidark.entity.ImageEntity;
import com.adidark.entity.OrderItemEntity;
import com.adidark.entity.ProductSizeEntity;
import com.adidark.model.dto.CartItemDTO;
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

        ProductSizeEntity productSizeEntity = orderItemEntity.getProductSizeEntity();
        if (productSizeEntity != null) {
            // Ensure product entity is also not null before accessing
            if (productSizeEntity.getProductEntity() != null) {
                orderItemDTO.setName(productSizeEntity.getProductEntity().getName());
                orderItemDTO.setProduct(productDTOConverter.toProductDTO(productSizeEntity.getProductEntity()));
                orderItemDTO.setSize(sizeDTOConverter.toSizeDTO(productSizeEntity.getSizeEntity()));
                orderItemDTO.setImages(productSizeEntity.getProductEntity()
                        .getImageList()
                        .stream()
                        .map(ImageEntity::getURL)
                        .toList());
                orderItemDTO.setProductSizeId(productSizeEntity.getId());
            } else {
                // Handle the case where ProductEntity is null (optional fallback behavior)
                orderItemDTO.setName("Product not available");
            }
        } else {
            // Handle the case where ProductSizeEntity is null (optional fallback behavior)
            orderItemDTO.setName("Product size not available");
        }

        // Setting remaining fields of OrderItemDTO
        orderItemDTO.setId(orderItemEntity.getId());
        orderItemDTO.setQuantity(orderItemEntity.getQuantity());
        orderItemDTO.setPrice(orderItemEntity.getPrice());
        orderItemDTO.setTotalPrice(orderItemEntity.getTotalPrice());

        // Set the OrderId only if OrderEntity exists
        if (orderItemEntity.getOrderEntity() != null) {
            orderItemDTO.setOrderId(orderItemEntity.getOrderEntity().getId());
        }

        return orderItemDTO;
    }

}
