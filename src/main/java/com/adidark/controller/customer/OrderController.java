package com.adidark.controller.customer;

import com.adidark.entity.CartEntity;
import com.adidark.entity.CartItemEntity;
import com.adidark.entity.ProductSizeEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;
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

    @PostMapping("/create")
    public String createOrder(
        @RequestParam Long userId,
        @RequestParam String address,
        @RequestParam List<Long> cartItemIds,
        @RequestParam BigDecimal totalPrice,
        Model model) {

        // Xử lý logic tạo đơn hàng ở đây
        OrderDTO order = orderService.createOrder(userId, address, cartItemIds, totalPrice);

        // Gắn thông tin đơn hàng vào model để hiển thị trong trang xác nhận
        model.addAttribute("order", order);

        // for testing
        model.addAttribute("phuc-test-message", );
        return "/customer/phuc-test";
    }
}
