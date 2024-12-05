package com.adidark.controller.customer;

import com.adidark.entity.ProductEntity;
import com.adidark.model.dto.*;
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

import java.math.BigDecimal;
import java.math.RoundingMode;
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

    @Autowired
    private UserService userService;

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
        UserDTO userDTO = userService.getUserDTOFromToken();
        model.addAttribute("userDTO", userDTO);
        return htmlFolderPath + "/product-list"; // Name of your Thymeleaf template
    }

    @GetMapping("/search")
    public String searchProducts(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "8") int size,
                                 @RequestParam(defaultValue = "") String namePattern,
                                 Model model) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        UserDTO userDTO = userService.getUserDTOFromToken();
        model.addAttribute("userDTO", userDTO);
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
        UserDTO userDTO = userService.getUserDTOFromToken();
        model.addAttribute("userDTO", userDTO);

        Page<ProductEntity> productPage = productService.filterByMultipleCriteria(namePattern, supplierIds, colorIds, sizeIds, pageable);
        prepareModelForwardedToProductList(model, productPage, page);
        System.out.println("Total elements: " + productPage.getTotalElements());
        return htmlFolderPath + "/product-search-list";
    }

    @GetMapping("/details")
    public String getProductDetails(@RequestParam(required = true) Long productId, Model model) {
        // Retrieve the product details
        ProductDTO productDTO = productService.findProductById(productId);

        // Ensure the product details are not null
        if (productDTO == null) {
            // Handle the case where the product is not found (e.g., return an error page)
            model.addAttribute("error", "Product not found");
            return "error"; // Adjust the template for error handling
        }

        // Apply discount only if discountPercent and price are not null
        BigDecimal discountPercent = productDTO.getDiscountPercent() != null ? productDTO.getDiscountPercent() : BigDecimal.ZERO;
        BigDecimal price = productDTO.getPrice() != null ? new BigDecimal(productDTO.getPrice()) : BigDecimal.ZERO;

        // Calculate discounted price
        BigDecimal discountedPrice = price.multiply(discountPercent).divide(new BigDecimal(100), RoundingMode.HALF_UP).setScale(2, RoundingMode.HALF_UP);

        // Add attributes to the model for Thymeleaf
        model.addAttribute("product", productDTO);
        model.addAttribute("discountedPrice", discountedPrice);

        // Retrieve the user and cart details
        UserDTO userDTO = userService.getUserDTOFromToken();
        if (userDTO != null) {
            CartDTO cart = cartService.findByUserId(userDTO.getId());
            model.addAttribute("cart", cart);
        } else {
            model.addAttribute("cart", null);  // No cart if user is not authenticated
        }

        // Return the Thymeleaf template for the cart item page
        return "customer/cart-item/add-cart-item";
    }

}
