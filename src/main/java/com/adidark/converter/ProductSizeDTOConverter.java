package com.adidark.converter;

import com.adidark.entity.ProductSizeEntity;
import com.adidark.entity.SizeEntity;
import org.springframework.stereotype.Component;

@Component
public class ProductSizeDTOConverter {

    public ProductSizeEntity toProductSizeEntity(SizeEntity sizeEntity, Integer stock){
        ProductSizeEntity productSizeEntity = new ProductSizeEntity();
        productSizeEntity.setSizeEntity(sizeEntity);
        productSizeEntity.setStock(stock);
        return productSizeEntity;
    }

}
