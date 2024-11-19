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
    public List<String> uploadImages(@RequestParam("images") MultipartFile[] files) {
        List<String> imageUrls = new ArrayList<>();

        for (MultipartFile file : files) {
            try {
                // Upload ảnh lên Cloudinary
                Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());

                // Lấy link ảnh từ kết quả upload
                String imageUrl = uploadResult.get("url").toString();
                imageUrls.add(imageUrl);

                // Lưu link ảnh vào cơ sở dữ liệu (ví dụ)
                System.out.println("Link ảnh: " + imageUrl);

            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Không thể upload file: " + file.getOriginalFilename());
            }
        }

        return imageUrls;
    }

}
