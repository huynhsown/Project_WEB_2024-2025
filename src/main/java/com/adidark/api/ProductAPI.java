package com.adidark.api;

import com.adidark.model.dto.ProductDTO;
import com.adidark.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/api")
public class ProductAPI {

    @Autowired
    private ProductService productService;

    @GetMapping("/search-suggestions")
    public List<ProductDTO> searchSuggestions(@RequestParam(value = "query", required = false) String query){
        return productService.getSuggestions(query);
    }

}
