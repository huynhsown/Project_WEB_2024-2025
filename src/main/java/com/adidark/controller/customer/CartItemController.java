package com.adidark.controller.customer;

import com.adidark.converter.SizeDTOConverter;
import com.adidark.entity.CartEntity;
import com.adidark.entity.CartItemEntity;
import com.adidark.entity.ProductSizeEntity;
import com.adidark.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.Optional;

@Controller
@RequestMapping("/customer/cart-item")
@Component("customerCartItemController")
public class CartItemController {

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private ProductSizeService productSizeService;

    @PostMapping("/add")
    public String addCartItem(
        @RequestParam Long cartId,
        @RequestParam Long productId,
        @RequestParam Long sizeId,
        @RequestParam Integer quantity,
        Model model) {

        /***
         *  Cập nhật số lượng sản phẩm của một mặt hàng mà khách muốn mua
         *  Ví dụ:
         *  Hiện tại khách hàng A đang có một đơn sản phẩm B, số lượng 2, tồn kho 1
         *  Với đầu vào quantity=3, thì kết quả sẽ là 3 sản phẩm B, tồn kho 0
         */

        // Lấy thông tin ProductSize
        ProductSizeEntity productSizeEntity = productSizeService.findByProductIdAndSizeId(productId, sizeId)
            .orElseThrow(() -> new RuntimeException("Product size not found, productId=" + productId + ";sizeId=" + sizeId));

        // Tìm cart item theo cartId và productSizeId
        Optional<CartItemEntity> optionalCartItem = cartItemService.findByCartIdAndProductSizeId(cartId, productSizeEntity.getId());

        CartItemEntity cartItem;
        int currentQuantityInCart = optionalCartItem.map(CartItemEntity::getQuantity).orElse(0);

        // Kiểm tra tồn kho: đảm bảo tổng số lượng mới không vượt tồn kho hiện tại
        if (productSizeEntity.getStock() + currentQuantityInCart < quantity) {
            model.addAttribute("error", "Insufficient stock");
            return "error-page"; // Tùy chỉnh tên trang lỗi
        }

        if (optionalCartItem.isPresent()) {
            // Nếu đã tồn tại, cập nhật số lượng và giá
            cartItem = optionalCartItem.get();
            int oldQuantity = cartItem.getQuantity();

            // Cập nhật tồn kho
            productSizeEntity.setStock(productSizeEntity.getStock() + oldQuantity - quantity);

            // Cập nhật thông tin giỏ hàng
            cartItem.setQuantity(quantity);
            BigDecimal productPrice = new BigDecimal(productService.findProductById(productId).getPrice());
            cartItem.setPrice(productPrice);
            cartItem.setTotalPrice(cartItem.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));

        } else {
            // Nếu chưa tồn tại, tạo mới
            CartEntity cartEntity = cartService.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

            cartItem = new CartItemEntity();
            cartItem.setCartEntity(cartEntity);
            cartItem.setProductSizeEntity(productSizeEntity);
            cartItem.setQuantity(quantity);

            // Cập nhật tồn kho
            productSizeEntity.setStock(productSizeEntity.getStock() - quantity);

            BigDecimal productPrice = new BigDecimal(productService.findProductById(productId).getPrice());
            cartItem.setPrice(productPrice);
            cartItem.setTotalPrice(cartItem.getPrice().multiply(BigDecimal.valueOf(quantity)));
        }

        // Lưu thông tin cart item và product size
        cartItemService.save(cartItem);
        productSizeService.save(productSizeEntity);

        // Cập nhật giá trị của giỏ hàng
        updateCartTotalPrice(cartId);

        return "redirect:/customer/products"; // Chuyển hướng về trang chur
    }

    public void updateCartTotalPrice(Long cartId){
        // Lấy thông tin CartEntity của người dùng
        CartEntity cartEntity = cartService.findById(cartId)
            .orElseThrow(() -> new RuntimeException("Cart not found"));

        // Tính tổng TotalPrice từ các CartItem
        BigDecimal totalPrice = cartEntity.getCartItemList().stream()
            .map(CartItemEntity::getTotalPrice) // Lấy TotalPrice của từng CartItem
            .reduce(BigDecimal.ZERO, BigDecimal::add); // Cộng tất cả TotalPrice lại

        // Cập nhật TotalPrice của CartEntity
        cartEntity.setTotalPrice(totalPrice);

        // Lưu CartEntity
        cartService.save(cartEntity);
    }

    @GetMapping("/delete")
    public String deleteCartItem (
        @RequestParam Long cartItemId,
        Model model
    ) {
        // Lấy thông tin giỏ hàng
        Long cartId = cartItemService.findById(cartItemId)
            .orElseThrow(() -> new RuntimeException("Cart Item not found"))
            .getCartEntity().getId();
        cartItemService.delete(cartItemId);
        // Cập nhật giá trị của giỏ hàng
        updateCartTotalPrice(cartId);
        return "redirect:/customer/products";
    }
}
