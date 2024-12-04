package com.adidark.controller.customer;

import com.adidark.converter.CartItemDTOConverter;
import com.adidark.converter.SizeDTOConverter;
import com.adidark.entity.CartEntity;
import com.adidark.entity.CartItemEntity;
import com.adidark.entity.ProductSizeEntity;
import com.adidark.model.dto.CartItemDTO;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Autowired
    private CartItemDTOConverter cartItemDTOConverter;

    @PostMapping("/add")
    public String addCartItem(
        @RequestParam Long cartId,
        @RequestParam Long productId,
        @RequestParam Long sizeId,
        @RequestParam Integer quantity,
        Model model) {

        /***
         *  Cập nhật số lượng sản phẩm của một mặt hàng mà khách muốn mua
         *  Chỉ kiểm tra stock có hợp lệ hay không, không thay đổi stock
         */

        // Lấy thông tin ProductSize
        ProductSizeEntity productSizeEntity = productSizeService.findByProductIdAndSizeId(productId, sizeId)
            .orElseThrow(() -> new RuntimeException("Product size not found, productId=" + productId + ";sizeId=" + sizeId));

        // Tìm cart item theo cartId và productSizeId
        Optional<CartItemEntity> optionalCartItem = cartItemService.findByCartIdAndProductSizeId(cartId, productSizeEntity.getId());

        CartItemEntity cartItem;
//        int currentQuantityInCart = optionalCartItem.map(CartItemEntity::getQuantity).orElse(0);

        // Kiểm tra tồn kho: đảm bảo tổng số lượng mới không vượt tồn kho hiện tại
        if (productSizeEntity.getStock() < quantity) {
            model.addAttribute("error", "Insufficient stock");
            return "error-page"; // Tùy chỉnh tên trang lỗi
        }

        if (optionalCartItem.isPresent()) {
            // Nếu đã tồn tại, cập nhật số lượng và giá
            cartItem = optionalCartItem.get();
            int oldQuantity = cartItem.getQuantity();

            // Cập nhật tồn kho
//            productSizeEntity.setStock(productSizeEntity.getStock() + oldQuantity - quantity);

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
//            productSizeEntity.setStock(productSizeEntity.getStock() - quantity);

            BigDecimal productPrice = new BigDecimal(productService.findProductById(productId).getPrice());
            cartItem.setPrice(productPrice);
            cartItem.setTotalPrice(cartItem.getPrice().multiply(BigDecimal.valueOf(quantity)));
        }

        // Lưu thông tin cart item và product size
        cartItemService.save(cartItem);
//        productSizeService.save(productSizeEntity);

        // Cập nhật giá trị của giỏ hàng
        cartService.updateCartTotalPrice(cartId);

        return "redirect:/customer/products"; // Chuyển hướng về trang chur
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
        cartService.updateCartTotalPrice(cartId);
        return "redirect:/customer/products";
    }

    @GetMapping("/show-for-create-order")
    public String showCartItemsForCreateOrder(
        @RequestParam Long userId,
        @RequestParam List<Long> cartItemIds,
        Model model) {

        // Lấy danh sách các CartItemEntity theo ID
        List<CartItemDTO> cartItemDTOList = cartItemIds.stream()
            .map(cartItemService::findById) // Gọi service để tìm từng CartItemEntity
            .flatMap(Optional::stream) // Lọc bỏ các giá trị Optional.empty()
            .map(cartItemDTOConverter::toCartItemDTO) // Chuyển đổi từ Entity sang DTO
            .collect(Collectors.toList()); // Tập hợp thành danh sách DTO

        // Tính tổng giá
        BigDecimal totalPrice = cartItemDTOList.stream()
            .map(CartItemDTO::getTotalPrice)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Gắn danh sách và các giá trị vào model
        model.addAttribute("userId", userId);
        model.addAttribute("cartItems", cartItemDTOList);
        model.addAttribute("totalPrice", totalPrice);

        // Gắn chuỗi cartItemIds vào model
        String cartItemIdsString = cartItemIds.stream()
            .map(String::valueOf)
            .collect(Collectors.joining(","));
        model.addAttribute("cartItemIdsString", cartItemIdsString);

        // Trả về tên file template
        return "/customer/cart-item/cart-item-list-for-create-order";
    }



}
