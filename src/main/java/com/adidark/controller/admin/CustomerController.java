package com.adidark.controller.admin;

import com.adidark.entity.UserEntity;
import com.adidark.model.dto.RoleDTO;
import com.adidark.model.dto.SuperClassDTO;
import com.adidark.model.dto.UserDTO;
import com.adidark.service.RoleService;
import com.adidark.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class CustomerController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @GetMapping("/customers")
    public ModelAndView show(@RequestParam(value = "query", required = false) String query,
                             @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                             HttpServletRequest req)
    {
        Sort sortby=Sort.by(Sort.Direction.ASC, "id");
        Pageable pageable= PageRequest.of(page, 10, sortby);
        SuperClassDTO<UserDTO> userList=userService.searchUser(query,pageable);
        ModelAndView mav=new ModelAndView("admin/customer");
        mav.addObject("userList",userList);
        mav.addObject("currentPath",req.getRequestURI());
        return  mav;
    }

    @GetMapping("/customer")
    public ModelAndView showCustomerById(@RequestParam(value = "id",required = false) Integer id,
                                         HttpServletRequest req)
    {
        UserEntity customer=userService.getUser(id);
        ModelAndView mav=new ModelAndView("admin/detail_customer");
        List<RoleDTO> listRole=roleService.getAllRole();
        mav.addObject("customer",customer);
        mav.addObject("currentPath",req.getRequestURI());
        mav.addObject("listRole" ,listRole);
        return mav;
    }


}
