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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin")
public class DiscountController {
    /*
    * KHÔNG CÓ CONTROLLER NÀO DÙNG ĐƯỢC
    * */
    @Autowired
    private DiscountService discountService;

    @Autowired
    private DiscountDTOConverter discountDTOConverter;

    @GetMapping("/discounts")
    public ModelAndView show(
        @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
        HttpServletRequest req,
        HttpSession session)
    {
        return null;
    }
    @GetMapping("/discounts/search")
    public ModelAndView searchDiscountByName(
        @RequestParam String name,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<DiscountEntity> discountEntities = discountService.searchDiscountByName(name, pageable);
        Page<DiscountDTO> discountDTOs = discountDTOConverter.toDTOPage(discountEntities);

        ModelAndView mav = new ModelAndView("discount-dto-list");
        mav.addObject("discounts", discountDTOs.getContent());
        mav.addObject("currentPage", page);
        mav.addObject("totalPages", discountDTOs.getTotalPages());
        mav.addObject("searchQuery", name);
        return mav;
    }

    @GetMapping("/discounts/sort")
    public ModelAndView sortDiscountByPercent(
        @RequestParam(defaultValue = "true") boolean ascending,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<DiscountEntity> discountEntities = discountService.sortDiscountByPercent(ascending, pageable);
        Page<DiscountDTO> discountDTOs = discountDTOConverter.toDTOPage(discountEntities);

        ModelAndView mav = new ModelAndView("discount-dto-list");
        mav.addObject("discounts", discountDTOs.getContent());
        mav.addObject("currentPage", page);
        mav.addObject("totalPages", discountDTOs.getTotalPages());
        mav.addObject("sortAscending", ascending);
        return mav;
    }
}
