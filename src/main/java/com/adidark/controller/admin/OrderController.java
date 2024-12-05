package com.adidark.controller.admin;

import com.adidark.enums.StatusType;
import com.adidark.model.dto.OrderDTO;
import com.adidark.model.dto.ProductDTO;
import com.adidark.model.dto.SuperClassDTO;
import com.adidark.service.OrderService;
import com.adidark.service.ProductService;
import com.adidark.service.UserService;
import com.adidark.service.impl.OrderServiceImpl;
import com.adidark.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class OrderController {

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;



    @GetMapping("/test")
    public List<ProductDTO> get(){
        return productService.getSuggestions("lec");
    }

    @GetMapping("/orders")
    public ModelAndView showOrder(@RequestParam(value = "query" ,required = false) String query,
                                  @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                  HttpServletRequest req){
        Sort sortBy = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(page, 10, sortBy);
        SuperClassDTO<OrderDTO> orderList=orderService.searchOrder(query,pageable);
        UserService userService=new UserServiceImpl();
        ModelAndView mav=new ModelAndView("admin/order");
        mav.addObject("orderList" ,orderList);
        mav.addObject("currentPath",req.getRequestURI());
        mav.addObject("userService",userService);
        return mav;
    }

    @GetMapping("/order/detail")
    public ModelAndView showOrderById(@RequestParam(value = "id",required = true) Long id,
                                      HttpServletRequest req){
        OrderDTO orderDTO=orderService.getOrder(id);
        OrderService service=new OrderServiceImpl();
        List<StatusType> statusList=orderService.getAllStatus();
        ModelAndView mav=new ModelAndView("admin/detail_order");
        mav.addObject("statusList",statusList);
        mav.addObject("service",service);
        mav.addObject("order",orderDTO);
        mav.addObject("currentPath",req.getRequestURI());
        return mav;
    }
    @GetMapping("/order/edit")
    public ModelAndView editOrder(@RequestParam(value = "id",required = true) Long id
    ,HttpServletRequest req){
        OrderService service=new OrderServiceImpl();
        OrderDTO order = orderService.getOrder(id);
        List<StatusType> statusList=orderService.getAllStatus();
        ModelAndView mav=new ModelAndView("admin/edit_order");
        mav.addObject("service",service);
        mav.addObject("statusList",statusList);
        mav.addObject("order",order);
        mav.addObject("currentPath",req.getRequestURI());
        return mav;
    }

}
