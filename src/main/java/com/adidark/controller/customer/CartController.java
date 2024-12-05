package com.adidark.controller.customer;

import com.adidark.model.dto.CartDTO;
import com.adidark.model.dto.UserDTO;
import com.adidark.service.CartService;
import com.adidark.service.UserService;
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

    @Autowired
    private UserService userService;

    private final String htmlFolderPath = "customer/cart";

    @GetMapping
    public String getAllCartItems(Model model) {
        UserDTO userDTO = userService.getUserDTOFromToken();
        CartDTO cart = cartService.findByUsername(userDTO.getUserName());
        model.addAttribute("cart", cart);
        model.addAttribute("userDTO", userDTO);
        return htmlFolderPath + "/user-cart"; // Name of your Thymeleaf template
    }
}
