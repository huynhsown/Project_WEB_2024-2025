package com.adidark.controller.admin;

import com.adidark.converter.DiscountDTOConverter;
import com.adidark.entity.DiscountEntity;
import com.adidark.model.dto.DiscountDTO;
import com.adidark.model.dto.ProductDTO;
import com.adidark.model.dto.SuperClassDTO;
import com.adidark.service.DiscountService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin/discounts")
public class DiscountController {

    @Autowired
    private DiscountService discountService;

    @Autowired
    private DiscountDTOConverter discountDTOConverter;

    @GetMapping
    public ModelAndView showDiscounts(@RequestParam(defaultValue = "0") int page) {
        ModelAndView modelAndView = new ModelAndView("admin/discount/list");

        // Lấy danh sách các Discount với phân trang
        Page<DiscountDTO> discounts = discountService.getAllDiscounts(PageRequest.of(page, 10));

        // Thêm thông tin phân trang vào ModelAndView
        modelAndView.addObject("discounts", discounts);
        modelAndView.addObject("currentPage", page); // Trang hiện tại
        modelAndView.addObject("totalPages", discounts.getTotalPages()); // Tổng số trang
        modelAndView.addObject("totalItems", discounts.getTotalElements()); // Tổng số phần tử

        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView showCreateForm() {
        ModelAndView modelAndView = new ModelAndView("admin/discount/create");
        modelAndView.addObject("discountDTO", new DiscountDTO());
        return modelAndView; // Trả về ModelAndView
    }

    @PostMapping("/create")
    public String createDiscount(@ModelAttribute DiscountDTO discountDTO) {
        discountService.createDiscount(discountDTO);
        return "redirect:/admin/discounts"; // Chuyển hướng về trang danh sách
    }

    @GetMapping("/edit/{id}")
    public ModelAndView showEditForm(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("admin/discount/edit");
        DiscountEntity discountEntity = discountService.findById(id).orElseThrow(() -> new RuntimeException("Discount not found"));
        DiscountDTO discountDTO = discountDTOConverter.toDTO(discountEntity);
        modelAndView.addObject("discountDTO", discountDTO);
        return modelAndView; // Trả về ModelAndView
    }

    @PostMapping("/edit/{id}")
    public String updateDiscount(@PathVariable Long id, @ModelAttribute DiscountDTO discountDTO) {
        discountService.updateDiscount(id, discountDTO);
        return "redirect:/admin/discounts"; // Chuyển hướng về trang danh sách
    }

    @GetMapping("/delete/{id}")
    public String deleteDiscount(@PathVariable Long id) {
        discountService.deleteDiscount(id);
        return "redirect:/admin/discounts"; // Chuyển hướng về trang danh sách
    }
}


