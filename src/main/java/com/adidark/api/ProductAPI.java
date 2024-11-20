package com.adidark.api;

import com.adidark.model.dto.ProductDTO;
import com.adidark.service.ProductService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/api")
public class ProductAPI {

    @Autowired
    private ProductService productService;

    @Autowired
    private Cloudinary cloudinary;

    @GetMapping("/search-suggestions")
    public List<ProductDTO> searchSuggestions(@RequestParam(value = "query", required = false) String query){
        return productService.getSuggestions(query);
    }

    @PostMapping("/save")
    public List<String> uploadImages(
            @ModelAttribute ProductDTO test,
            @RequestParam("images") MultipartFile[] files
    )
    {
        System.out.println(test);
        for(String color : test.getColors()) System.out.println(color);
        return null;
    }
}
