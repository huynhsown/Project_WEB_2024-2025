package com.adidark.controller;

import com.adidark.entity.ColorEntity;
import com.adidark.entity.ProductEntity;
import com.adidark.entity.ProductSizeEntity;
import com.adidark.entity.SizeEntity;
import com.adidark.service.ColorService;
import com.adidark.service.ProductService;
import com.adidark.service.SizeService;
import com.adidark.service.SupplierService;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private SupplierService supplierService;

    @Autowired
    private ColorService colorService;

    @Autowired
    private SizeService sizeService;

    private final String htmlFolderPath = "/views/product";

    private void prepareModelForwardedToProductList(Model model, Page<ProductEntity> productPage, int page){
        model.addAttribute("products", productPage.getContent());
        model.addAttribute("isEmpty", productPage.getContent().isEmpty());
        model.addAttribute("totalElements", productPage.getTotalElements());
        model.addAttribute("totalPages", productPage.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("suppliers", supplierService.findAll());
        model.addAttribute("colors", colorService.findAll());
        model.addAttribute("sizes", sizeService.findAll());

    }

    // Endpoint to show the paginated product list
    @GetMapping
    public String getAllProducts(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int size,
                                 Model model) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductEntity> productPage = productService.findAll(pageable);
        prepareModelForwardedToProductList(model, productPage, page);
        return htmlFolderPath + "/product-list"; // Name of your Thymeleaf template
    }

    @GetMapping("/search")
    public String searchProducts(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int size,
                                 @RequestParam(defaultValue = "") String namePattern,
                                 @RequestParam(required = false) List<Long> supplierIds,
                                 @RequestParam(required = false) List<Long> colorIds,
                                 @RequestParam(required = false) List<Long> sizeIds,
                                 @RequestParam(required = false, defaultValue = "priceAsc") String sort,
                                 Model model) {
        Pageable pageable = PageRequest.of(page, size);
        model.addAttribute("namePattern", namePattern); // To keep the search input populated
        Page<ProductEntity> productPage = productService.findByNameContainingIgnoreCase(namePattern, pageable);
        prepareModelForwardedToProductList(model, productPage, page);
        return htmlFolderPath + "/product-search-list";
    }

//    @GetMapping("/filter")
//    public String filterProducts(@RequestParam(defaultValue = "0") int page,
//                                 @RequestParam(defaultValue = "10") int size,
//                                 @RequestParam(defaultValue = "") String namePattern,
//                                 @RequestParam(required = false) List<Long> supplierIds,
//                                 @RequestParam(required = false, defaultValue = "priceAsc") String sort,
//                                 Model model) {
//        Pageable pageable;
//        if ("priceAsc".equals(sort)) {
//            pageable = PageRequest.of(page, size, Sort.by("price").ascending());
//        } else {
//            pageable = PageRequest.of(page, size, Sort.by("price").descending());
//        }
//
//        model.addAttribute("selectedSuppliers", supplierIds);
//        model.addAttribute("selectedSort", sort);
//        model.addAttribute("namePattern", namePattern);
//        Page<ProductEntity> productPage = productService.filterByMultipleSuppliers(namePattern, supplierIds, pageable);
//        prepareModelForwardedToProductList(model, productPage, page);
//
//        return htmlFolderPath + "/product-search-list";
//    }

    @GetMapping("/filter")
    public String filterProducts(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int size,
                                 @RequestParam(defaultValue = "") String namePattern,
                                 @RequestParam(required = false) List<Long> supplierIds,
                                 @RequestParam(required = false) List<Long> colorIds,
                                 @RequestParam(required = false) List<Long> sizeIds,
                                 @RequestParam(required = false, defaultValue = "priceAsc") String sort,
                                 Model model) {
        Pageable pageable;
        if ("priceAsc".equals(sort)) {
            pageable = PageRequest.of(page, size, Sort.by("price").ascending());
        } else {
            pageable = PageRequest.of(page, size, Sort.by("price").descending());
        }

        model.addAttribute("selectedSuppliers", supplierIds);
        model.addAttribute("selectedColors", colorIds);
        model.addAttribute("selectedSizes", sizeIds);
        model.addAttribute("selectedSort", sort);
        model.addAttribute("namePattern", namePattern);


        Page<ProductEntity> productPage = productService.filterByMultipleCriteria(namePattern, supplierIds, colorIds, sizeIds, pageable);
        prepareModelForwardedToProductList(model, productPage, page);

        return htmlFolderPath + "/product-search-list";
    }



}
