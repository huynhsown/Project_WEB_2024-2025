package com.adidark.controller.customer;

import com.adidark.entity.ProductEntity;
import com.adidark.model.dto.CartDTO;
import com.adidark.model.dto.ProductDTO;
import com.adidark.model.dto.SuperClassDTO;
import com.adidark.model.dto.SupplierDTO;
import com.adidark.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/customer/products")
@Component("customerProductController")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private SupplierService supplierService;

    @Autowired
    private ColorService colorService;

    @Autowired
    private SizeService sizeService;

    @Autowired
    private CartService cartService;

    private final String htmlFolderPath = "/customer/product";

    private void prepareModelForwardedToProductList(Model model, Page<ProductEntity> productPage, int page){
        model.addAttribute("products", productPage.getContent());
        model.addAttribute("isEmpty", productPage.getContent().isEmpty());
        model.addAttribute("totalElements", productPage.getTotalElements());
        model.addAttribute("totalPages", productPage.getTotalPages());
        model.addAttribute("currentPage", page);
        List<SupplierDTO> supplierDTOs = supplierService.findAll();
        model.addAttribute("suppliers", supplierDTOs);
        model.addAttribute("colors", colorService.findAll());
        model.addAttribute("sizes", sizeService.findAll());

    }

    @GetMapping
    public String getAllProducts(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "8") int size,
                                 Model model) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductEntity> productPage = productService.findAll(pageable);
        prepareModelForwardedToProductList(model, productPage, page);
        return htmlFolderPath + "/product-list"; // Name of your Thymeleaf template
    }
//
//    @GetMapping("/mav")
//    public ModelAndView show(
//            @RequestParam(value = "query", required = false) String query,
//            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
//            HttpServletRequest req,
//            HttpSession session)
//    {
//        Sort sortBy = Sort.by(Sort.Direction.DESC, "id");
//        Pageable pageable = PageRequest.of(page, 12, sortBy);
//        // Integer totalItem = cartService.findById(101L).get().getCartItemList().size();
//        SuperClassDTO<ProductDTO> products = productService.searchProducts(query, pageable);
//        ModelAndView mav = new ModelAndView("customer/product/product-test");
//
//        mav.addObject("currentPath", req.getRequestURI());
////        mav.addObject("totalItem", totalItem);
//        mav.addObject("userid", "lstuckow16993");
//        mav.addObject("products", products);
//        return mav;
//    }

    @GetMapping("/search")
    public String searchProducts(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "8") int size,
                                 @RequestParam(defaultValue = "") String namePattern,
                                 Model model) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        model.addAttribute("namePattern", namePattern); // To keep the search input populated
        Page<ProductEntity> productPage = productService.findByNameContainingIgnoreCase(namePattern, pageable);
        prepareModelForwardedToProductList(model, productPage, page);
        return htmlFolderPath + "/product-search-list";
    }

    @GetMapping("/filter")
    public String filterProducts(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "8") int size,
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

        model.addAttribute("selectedSupplierIds", supplierIds);
        model.addAttribute("selectedColorIds", colorIds);
        model.addAttribute("selectedSizeIds", sizeIds);
        model.addAttribute("selectedSort", sort);
        model.addAttribute("namePattern", namePattern);

        Page<ProductEntity> productPage = productService.filterByMultipleCriteria(namePattern, supplierIds, colorIds, sizeIds, pageable);
        prepareModelForwardedToProductList(model, productPage, page);
        System.out.println("Total elements: " + productPage.getTotalElements());
        return htmlFolderPath + "/product-search-list";
    }


    // -------------- DTO ZONE --------------------
    @GetMapping("/details")
    public String getProductDetails(@RequestParam(required = true) Long productId, Model model) {
        // Add the product to the model
        model.addAttribute("product", productService.findProductById(productId));
        Long userId = 1L;
        CartDTO cart = cartService.findByUserId(userId);
        model.addAttribute("cart", cart);
        return "customer/cart-item/add-cart-item"; // Name of the Thymeleaf template
    }

}
