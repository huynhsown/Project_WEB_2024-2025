package com.adidark.controller.customer;

import com.adidark.converter.SizeDTOConverter;
import com.adidark.entity.*;
import com.adidark.model.dto.CartDTO;
import com.adidark.model.dto.ProductDTO;
import com.adidark.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Optional;

@RestController
@RequestMapping("/test/customer")
public class TestController {

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

    @Autowired
    private SizeService sizeService;

    @Autowired
    private SizeDTOConverter sizeDTOConverter;

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private ProductSizeService productSizeService;

    @GetMapping("/cart")
    public CartDTO getUserCart(@RequestParam(required = true) Long userId) {
        return cartService.findByUserId(userId);
    }

    @GetMapping("/product")
    public ProductDTO getProduct(@RequestParam(required = true) Long productId) {
        return productService.findProductById(productId);
    }


    @GetMapping("/cart-item/get")
    public CartItemEntity getCartItem(@RequestParam(required = true) Long cartId,
                                      @RequestParam(required = true) Long productSizeId) {
        return cartItemService.findByCartIdAndProductSizeId(cartId, productSizeId).get();
    }

    @GetMapping("/product-size/get")
    public ProductSizeEntity getProductSize(@RequestParam(required = true) Long productId,
                                            @RequestParam(required = true) Long sizeId) {
        return productSizeService.findByProductIdAndSizeId(productId, sizeId).get();
    }

    @PostMapping("/cart-item/add")
    public String addCartItem(
        @RequestParam Long productId,
        @RequestParam Long sizeId,
        @RequestParam Long cartId,
        @RequestParam Integer quantity,
        Model model) {

        // Lấy thông tin ProductSize
        ProductSizeEntity productSizeEntity = productSizeService.findByProductIdAndSizeId(productId, sizeId)
            .orElseThrow(() -> new RuntimeException("Product size not found"));

        // Kiểm tra tồn kho
        if (productSizeEntity.getStock() < quantity) {
            model.addAttribute("error", "Insufficient stock");
            return "error-page"; // Tùy chỉnh tên trang lỗi
        }

        // Nếu trong kho còn thì tạo thêm CartItem mới hoặc cập nhật nếu như đã có rồi
        Optional<CartItemEntity> optionalCartItemEntity = cartItemService.findByCartIdAndProductSizeId(cartId, productId);

        if (optionalCartItemEntity.isPresent()){

        }

        CartEntity cartEntity = cartService.findById(cartId)
            .orElseThrow(() -> new RuntimeException("Cart not found"));

        ProductEntity productEntity = productService.findEntityById(productId)
            .orElseThrow(() -> new RuntimeException("Product not found"));




        CartItemEntity cartItem = cartItemService.findByCartIdAndProductSizeId(cartId, productId)
            .orElseGet(() -> {
                // Nếu chưa tồn tại, tạo mới
                CartItemEntity newItem = new CartItemEntity();
                newItem.setCartEntity(cartEntity);
                // newItem.setProductEntity(productEntity);
                return newItem;
            });
        Integer oldQuantity = cartItem.getQuantity();
        // Cập nhật thông tin CartItem
        cartItem.setQuantity(quantity);
        cartItem.setPrice(productEntity.getPrice());
        cartItem.setTotalPrice(cartItem.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));

        // Giảm tồn kho
        productSizeEntity.setStock(productSizeEntity.getStock() + oldQuantity - quantity);

        // Lưu thông tin
        cartItemService.save(cartItem);
        productSizeService.save(productSizeEntity);

        return "redirect:/customer/cart?userId=1"; // Chuyển hướng về trang giỏ hàng
    }

}
