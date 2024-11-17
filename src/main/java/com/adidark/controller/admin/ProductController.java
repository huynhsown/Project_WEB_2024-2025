package com.adidark.controller.admin;

import com.adidark.model.dto.ProductDTO;
import com.adidark.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public ModelAndView show(HttpServletRequest req){

        List<ProductDTO> productList = new ArrayList<>();


        ModelAndView mav = new ModelAndView("admin/product");
        mav.addObject("currentPath", req.getRequestURI());
        mav.addObject("products", productList);
        return mav;
    }
}
