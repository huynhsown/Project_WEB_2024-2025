package com.adidark.controller.admin;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin")
public class ReportController {
    @GetMapping("/report")
    public ModelAndView showReport(HttpServletRequest req){
        ModelAndView mav=new ModelAndView("admin/test3");
        mav.addObject("currentPath",req.getRequestURI());
        return mav;
    }
}
