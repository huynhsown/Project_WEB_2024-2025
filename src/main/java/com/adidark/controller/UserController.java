package com.adidark.controller;

import com.adidark.entity.UserEntity;
import com.adidark.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("")
    public String getAllUsers(Model model) {
        List<UserEntity> userList = userRepository.findAll();
        model.addAttribute("users", userList);
        return "user-list"; // Tên của template (user-list.html) trong thư mục templates
    }
}
