package com.adidark.controller.customer;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/customer/cart")
@Component("customerCashController")
public class CartController {
    private final String htmlFolderPath = "/customer/cart";
    @GetMapping
    public String getAllCashItems(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "6") int size,
                                 @RequestParam(required = true) Long userId,
                                 Model model) {

        return htmlFolderPath + "/user-cart"; // Name of your Thymeleaf template
    }
}
