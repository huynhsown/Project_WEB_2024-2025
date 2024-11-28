package com.adidark.api;

import com.adidark.model.dto.ProductDTO;
import com.adidark.model.response.ResponseDTO;
import com.adidark.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/api")
public class ProductAPI {

    @Autowired
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping("/search-suggestions")
    public List<ProductDTO> searchSuggestions(@RequestParam(value = "query", required = false) String query){
        return productService.getSuggestions(query);
    }

    @PostMapping("/save")
    public ResponseDTO saveProduct(
            @RequestPart("product") String productJson,
            @RequestPart(value = "images", required = false) MultipartFile[] images) throws JsonProcessingException {
        return productService.save(productJson, images);
    }

    @PostMapping("/edit")
    public ResponseDTO editProduct(
            @RequestPart("product") String productJson,
            @RequestPart(value = "images", required = false) MultipartFile[] images) throws JsonProcessingException {
        return productService.save(productJson, images);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseDTO deleteProduct(@PathVariable(value = "id") Long id){
        System.out.println(id);
        return productService.deleteById(id);
    }
}
