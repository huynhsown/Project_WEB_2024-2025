package com.adidark.controller.admin;

import com.adidark.model.dto.SuperClassDTO;
import com.adidark.model.dto.UserDTO;
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

@Controller
@RequestMapping("/admin")
public class UserController {

    @Autowired
    private UserService userService;

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
                                         @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                         HttpServletRequest req)
    {
        Sort sortby=Sort.by(Sort.Direction.ASC,"id");
        Pageable pageable=PageRequest.of(page,10,sortby);

        return null;
    }

}
