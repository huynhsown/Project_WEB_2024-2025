package com.adidark.controller.customer;

import com.adidark.entity.CartEntity;
import com.adidark.entity.CartItemEntity;
import com.adidark.entity.OrderEntity;
import com.adidark.entity.ProductSizeEntity;
import com.adidark.model.dto.CartDTO;
import com.adidark.service.CartItemService;
import com.adidark.service.CartService;
import com.adidark.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/customer/order")
@Component("customerOrderController")
public class OrderController {
    // TODO: CREATE ORDER FROM CREATE ORDER POST REQUEST FROM THE cart-item-list-for-create-order.html FILE
    /*
    <form action="/create-order" method="post">
            <!-- Địa chỉ -->
            <textarea name="address" placeholder="Enter your shipping address" rows="4" cols="50" required></textarea>
            <br>

            <!-- Hidden Inputs -->
            <!-- User ID -->
            <input type="hidden" name="userId" th:value="${userId}" />
            <!-- Total Price -->
            <input type="hidden" name="totalPrice" th:value="${totalPrice}" />
            <!-- Cart Item IDs -->
            <input type="hidden" name="cartItemIds" th:value="${cartItemIdsString}" />

            <!-- Tổng giá -->
            <h3>Total: <span th:text="${totalPrice + ' VND'}"></span></h3>

            <!-- Nút Create Order -->
            <button type="submit">Create Order</button>
        </form>
    * */

    @Autowired
    private OrderService orderService;

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private CartService cartService;

    @PostMapping("/create")
    public String createOrder(
        @RequestParam Long userId,
        @RequestParam String address,
        @RequestParam List<Long> cartItemIds,
        @RequestParam BigDecimal totalPrice,
        Model model) {
        
        Long addressId = 1L; // assume address id is 1

        List<CartItemEntity> cartItemEntities = cartItemService.findAllById(cartItemIds);
        Map<String, Object> validationResult = orderService.validCartItemEntitiesForOrdering2(cartItemEntities);
        boolean isValid = (boolean) validationResult.get("isValid");

        if (isValid){
            // Tạo một order mới cho userId
            // tạm thời orderItemsIds rỗng do chưa thêm order
            OrderEntity orderEntity = orderService.addOrder(userId, addressId, null);
            cartItemEntities
            .forEach(cartItemEntity -> orderService.addOrderItemToOrder(
                orderEntity.getId(),
                cartItemEntity
            ));
            model.addAttribute("message", address);

            // Cập nhật tổng giá của giỏ hàng hiện tại của người dùng
            CartDTO cartDTO = cartService.findByUserId(userId);
            cartService.updateCartTotalPrice(cartDTO.getId());

        }
        else {
            List<Long> invalidCartItemIds = (List<Long>) validationResult.get("invalidCartItemIds");
            model.addAttribute("message", "Không thể tạo order vì số lượng hàng trong kho đã không còn đủ");
            model.addAttribute("invalidCartItemIds", invalidCartItemIds);
        }
        
        return "/customer/phuc-test";
    }
}
