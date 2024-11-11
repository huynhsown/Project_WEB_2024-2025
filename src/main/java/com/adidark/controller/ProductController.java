package com.adidark.controller;

import com.adidark.entity.ProductEntity;
import com.adidark.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/v1")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products/delete")
    public String showFormDeleteProduct(Model model) {
        model.addAttribute("product", new ProductEntity());
        return "product/product-delete";
    }

    @DeleteMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {
        System.out.println("id=" + id);
        this.productService.deleteById(id);
        return "redirect:/v1/products";
    }

}
