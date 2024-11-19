package com.adidark.controller.admin;

import com.adidark.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class HomeController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String getAllUsers(Model model, HttpServletRequest req) {
        model.addAttribute("currentPath", req.getRequestURI());
        return "admin/home"; // Tên của template (user-list.html) trong thư mục templates
    }
}
