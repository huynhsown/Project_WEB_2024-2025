package com.adidark.controller.admin;

import com.adidark.model.dto.ProductDTO;
import com.adidark.model.dto.SuperClassDTO;
import com.adidark.service.CategoryService;
import com.adidark.service.ProductService;
import com.adidark.service.SupplierService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private SupplierService supplierService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/products")
    public ModelAndView show(
            @RequestParam(value = "query", required = false) String query,
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            HttpServletRequest req)
    {
        Sort sortBy = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(page, 10, sortBy);
        SuperClassDTO<ProductDTO> products = productService.searchProducts(query, pageable);
        ModelAndView mav = new ModelAndView("admin/product");
        mav.addObject("currentPath", req.getRequestURI());
        mav.addObject("products", products);
        return mav;
    }

    @GetMapping("/product/add")
    public ModelAndView addProduct(HttpServletRequest req){
        ModelAndView mav = new ModelAndView("admin/add_product");
        mav.addObject("suppliers", supplierService.findAll());
        mav.addObject("categories", categoryService.findAll());
        mav.addObject("currentPath", req.getRequestURI());
        return mav;
    }

    @GetMapping("/product/edit/{id}")
    public ModelAndView editProduct(@PathVariable(value = "id") Long id, HttpServletRequest req){
        ModelAndView mav = new ModelAndView("admin/edit_product");
        mav.addObject("product", productService.findById(id).get());
        mav.addObject("suppliers", supplierService.findAll());
        mav.addObject("categories", categoryService.findAll());
        mav.addObject("currentPath", req.getRequestURI());
        return mav;
    }
}
