package com.adidark.controller.admin;

import com.adidark.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin")
public class HomeController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/login")
    public ModelAndView getAllUsers() {
        return new ModelAndView("admin/login");
    }

}
