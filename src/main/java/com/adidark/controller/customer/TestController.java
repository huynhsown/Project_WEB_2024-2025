package com.adidark.controller.customer;

import com.adidark.converter.CartItemDTOConverter;
import com.adidark.converter.OrderItemDTOConverter;
import com.adidark.converter.SizeDTOConverter;
import com.adidark.entity.*;
import com.adidark.model.dto.CartDTO;
import com.adidark.model.dto.CartItemDTO;
import com.adidark.model.dto.OrderItemDTO;
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

    @Autowired
    private CartItemDTOConverter cartItemDTOConverter;

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private OrderItemDTOConverter orderItemDTOConverter;

    @GetMapping("/cartEntity")
    public CartEntity getUserCartEntity(@RequestParam(required = true) Long userId) {
        return cartItemService.findById(userId)
            .orElseThrow(() -> new RuntimeException("Cart Item not found"))
            .getCartEntity();
    }

    @GetMapping("/cart")
    public CartDTO getUserCart(@RequestParam(required = true) Long userId) {
        return cartService.findByUserId(userId);
    }

    @GetMapping("/product")
    public ProductDTO getProduct(@RequestParam(required = true) Long productId) {
        return productService.findProductById(productId);
    }

    @GetMapping("/cart-item/get-by-cart-and-productsize")
    public CartItemEntity getCartItem(@RequestParam(required = true) Long cartId,
                                      @RequestParam(required = true) Long productSizeId) {
        return cartItemService.findByCartIdAndProductSizeId(cartId, productSizeId).get();
    }

    @GetMapping("/cart-item/get")
    public CartItemEntity getCartItem(@RequestParam(required = true) Long cartItemId) {
        return cartItemService.findById(cartItemId).get();
    }

    @GetMapping("/cart-item-dto/get")
    public CartItemDTO getCartItemDTO(@RequestParam(required = true) Long cartItemId) {
        return cartItemDTOConverter.toCartItemDTO(cartItemService.findById(cartItemId).get());
    }

    @GetMapping("/product-size/get")
    public ProductSizeEntity getProductSize(@RequestParam(required = true) Long productId,
                                            @RequestParam(required = true) Long sizeId) {
        return productSizeService.findByProductIdAndSizeId(productId, sizeId).get();
    }

    @PostMapping("/cart-item/add")
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
            .orElseThrow(() -> new RuntimeException("Product size not found"));

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

        return "redirect:/customer/cart?userId=1"; // Chuyển hướng về trang giỏ hàng
    }

    // OrderItem test
    @GetMapping("/order-item/get")
    public OrderItemEntity getOrderItemEntity(@RequestParam(required = true) long orderItemId){
        return orderItemService.findById(orderItemId).orElseThrow(()-> new RuntimeException("NO OrderItem Found"));
    }

    @GetMapping("/order-item-dto/get")
    public OrderItemDTO getOrderItemDTO(@RequestParam(required = true) long orderItemId){
        return orderItemDTOConverter.toOrderItemDTO(orderItemService.findById(orderItemId)
            .orElseThrow(()-> new RuntimeException("NO OrderItem Found")));
    }

    @PostMapping("/order-item/add")
    public String addOrderItem(@RequestParam Long orderId,
                               @RequestParam Long productSizeId,
                               @RequestParam Integer quantity,
                               @RequestParam BigDecimal price,
                               Model model) {
        try {
            // Gọi service để thêm OrderItem
            orderItemService.addOrderItem(orderId, productSizeId, quantity, price);
            System.out.println("Order item added successfully!");
        } catch (Exception e) {
            System.out.println("Failed to add order item: " + e.getMessage());
        }

        return "redirect:/customer/order";
    }

}
