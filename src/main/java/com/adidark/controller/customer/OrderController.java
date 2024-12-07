package com.adidark.controller.customer;

import com.adidark.entity.CartEntity;
import com.adidark.entity.CartItemEntity;
import com.adidark.entity.OrderEntity;
import com.adidark.entity.ProductSizeEntity;
import com.adidark.model.dto.CartDTO;
import com.adidark.model.dto.UserDTO;
import com.adidark.service.CartItemService;
import com.adidark.service.CartService;
import com.adidark.service.OrderService;
import com.adidark.service.UserService;
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

    @Autowired
    private OrderService orderService;

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public String createOrder(
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
            UserDTO userDTO = userService.getUserDTOFromToken();
            OrderEntity orderEntity = orderService.addOrder(userDTO.getId(), address, null);
            cartItemEntities
            .forEach(cartItemEntity -> orderService.addOrderItemToOrder(
                orderEntity.getId(),
                cartItemEntity
            ));
            model.addAttribute("message", address);

            // Cập nhật tổng giá của giỏ hàng hiện tại của người dùng
            CartDTO cartDTO = cartService.findByUserId(userDTO.getId());
            cartService.updateCartTotalPrice(cartDTO.getId());

        }
        else {
            List<Long> invalidCartItemIds = (List<Long>) validationResult.get("invalidCartItemIds");
            model.addAttribute("message", "Không thể tạo order vì số lượng hàng trong kho đã không còn đủ");
            model.addAttribute("invalidCartItemIds", invalidCartItemIds);
        }
        
        return "redirect:/customer/orders";
    }
}
