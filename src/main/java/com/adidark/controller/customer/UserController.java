package com.adidark.controller.customer;

import com.adidark.converter.OrderDTOConverter;
import com.adidark.entity.OrderEntity;
import com.adidark.entity.UserEntity;
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

    @Autowired
    private OrderDTOConverter orderDTOConverter;

    @GetMapping("/register")
    public ModelAndView registerAccount(){
        ModelAndView mav = new ModelAndView("customer/test");
        return mav;
    }

    @GetMapping("/login")
    public ModelAndView login(){
        return new ModelAndView("customer/login");
    }

    @GetMapping("/customer/{username}/orders")
    public ModelAndView showOrders(@PathVariable("username") String userName) {
        UserEntity userEntity = userService.findByUserName(userName);
        List<OrderDTO> orderDTOList = userEntity.getOrderList()
                .stream()
                .map(item -> orderDTOConverter.toOrderDTO(item))
                .toList();
        //
        //
        ModelAndView mav = new ModelAndView("customer/orders");
        mav.addObject("orderDTOList", orderDTOList);
        mav.addObject("username", "lstuckow16993");
        return mav;
    }

    @GetMapping("/customer/{username}/order/detail/{orderId}")
    public ModelAndView showOrderDetail(
            @PathVariable("username") String userName,
            @PathVariable("orderId") Long orderId
    ){
        UserEntity userEntity = userService.findByUserName(userName);
        OrderDTO orderDTO = orderService.findById(orderId);

        ModelAndView mav = new ModelAndView("customer/order-detail");
        mav.addObject("orderDTO", orderDTO);
        return mav;
    }

}
