package com.adidark.controller.admin;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class OrderController {

    @GetMapping("/orders")
    private String getOrders(Model model, HttpServletRequest req){
        model.addAttribute("currentPath", req.getRequestURI());
        return "admin/order";
    }

}
