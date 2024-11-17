package com.adidark.controller.admin;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class ProductController {

    @GetMapping("/products")
    public String show(Model model, HttpServletRequest req){
        model.addAttribute("currentPath", req.getRequestURI());
        return "admin/product";
    }

}
