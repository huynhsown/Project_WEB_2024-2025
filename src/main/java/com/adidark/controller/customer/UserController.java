package com.adidark.controller.customer;

import org.springframework.stereotype.Component;
import com.adidark.model.dto.UserDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Component("customerUserController")
public class UserController {

    @GetMapping("/register")
    public ModelAndView registerAccount(){
        ModelAndView mav = new ModelAndView("customer/test");
        return mav;
    }

    @GetMapping("/login")
    public ModelAndView login(){
        return new ModelAndView("customer/login");
    }
}
