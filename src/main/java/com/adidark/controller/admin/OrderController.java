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
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class OrderController {

    @Autowired
    private ProductService productService;

    @GetMapping("/test")
    public List<ProductDTO> get(){
        return productService.getSuggestions("lec");
    }

}
