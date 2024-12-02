package com.adidark.controller.customer;

import com.adidark.model.dto.CartDTO;
import com.adidark.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/customer/cart")
@Component("customerCartController")
public class CartController {

    @Autowired
    private CartService cartService;

    private final String htmlFolderPath = "customer/cart";

    @GetMapping
    public String getAllCartItems(@RequestParam(required = true) Long userId, Model model) {
        CartDTO cart = cartService.findByUserId(userId);
        model.addAttribute("cart", cart);
        model.addAttribute("userId", userId);
        return htmlFolderPath + "/user-cart"; // Name of your Thymeleaf template
    }
}
