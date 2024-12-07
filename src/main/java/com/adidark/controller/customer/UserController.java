package com.adidark.controller.customer;

import com.adidark.model.dto.OrderDTO;
import com.adidark.service.OrderService;
import com.adidark.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.adidark.model.dto.UserDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@Component("customerUserController")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;


    @GetMapping("/register")
    public ModelAndView registerAccount(){
        ModelAndView mav = new ModelAndView("customer/test");
        return mav;
    }

    @GetMapping("/login")
    public ModelAndView login(){
        return new ModelAndView("customer/login");
    }

    @GetMapping("/customer/orders")
    public ModelAndView showOrders() {

        UserDTO userDTO = userService.getUserDTOFromToken();
        List<OrderDTO> orderDTOList = orderService.findByUserName(userDTO.getUserName());
        //
        //
        ModelAndView mav = new ModelAndView("customer/orders");
        mav.addObject("userDTO", userDTO);
        mav.addObject("orderDTOList", orderDTOList);

        return mav;
    }

    @GetMapping("/customer/order/detail/{orderId}")
    public ModelAndView showOrderDetail(
            @PathVariable("orderId") Long orderId
    ){
        OrderDTO orderDTO = orderService.findById(orderId);
        ModelAndView mav = new ModelAndView("customer/order-detail");
        mav.addObject("orderDTO", orderDTO);
        UserDTO userDTO = userService.getUserDTOFromToken();
        mav.addObject("userDTO", userDTO);
        return mav;
    }

    @GetMapping("/customer/account/detail")
    public ModelAndView showAccountDetail(){
        UserDTO userDTO = userService.getUserDTOFromToken();
        ModelAndView mav = new ModelAndView("customer/account-detail");
        mav.addObject("userDTO", userDTO);
        return mav;
    }
}
