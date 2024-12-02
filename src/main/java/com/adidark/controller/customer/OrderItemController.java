package com.adidark.controller.customer;

import com.adidark.entity.CartEntity;
import com.adidark.entity.CartItemEntity;
import com.adidark.entity.ProductSizeEntity;
import com.adidark.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/customer/order-item")
@Component("customerOrderItemController")
public class OrderItemController {

    @Autowired
    private CartService cartService;

    @GetMapping
    public String showOrderItems(
        @RequestParam List<Long> cartItemIds,
        Model model) {
        // Convert the list of cartItemIds to a comma-separated string
        String cartItemIdsString = cartItemIds.stream()
            .map(String::valueOf) // Convert Long to String
            .collect(Collectors.joining(","));
        // Optionally add to the model if needed
        model.addAttribute("cartItemIdsString", cartItemIdsString);

        return "/customer/phuc-test";
    }

    @PostMapping("/create-order")
    public String createOrder (
        @RequestParam List<Long> xxxxx,
        Model model) {

        return "redirect:/customer/products";
    }

}
