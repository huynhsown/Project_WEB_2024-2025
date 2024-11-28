package com.adidark.service.impl;

import com.adidark.converter.*;
import com.adidark.entity.*;
import com.adidark.model.dto.ProductDTO;
import com.adidark.model.dto.SuperClassDTO;
import com.adidark.model.response.ResponseDTO;
import com.adidark.repository.*;
import com.adidark.service.ProductService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private ColorRepository colorRepository;

    @Autowired
    private SizeRepository sizeRepository;

    @Autowired
    private ProductSizeRepository productSizeRepository;

    @Autowired
    private ProductDTOConverter productDTOConverter;

    @Autowired
    private ColorDTOConverter colorDTOConverter;

    @Autowired
    private ImageDTOConverter imageDTOConverter;

    @Autowired
    private SizeDTOConverter sizeDTOConverter;

    @Autowired
    private ProductSizeDTOConverter productSizeDTOConverter;

    @Autowired
    private Cloudinary cloudinary;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public SuperClassDTO<ProductDTO> searchProducts(String query, Pageable pageable) {
        Page<ProductEntity> products = null;
        if(!StringUtils.isEmptyOrWhitespace(query)){
            products = productRepository.findByNameContainingIgnoreCase(query, pageable);
        }
        else{
            products = productRepository.findAll(pageable);
        }

        SuperClassDTO<ProductDTO> result = new SuperClassDTO<>();
        result.setTotalPage(products.getTotalPages());
        result.setCurrentPage(pageable.getPageNumber());
        result.setSearchValue(query);
        result.setItems(products.stream()
                .map(item -> productDTOConverter.toProductDTO(item))
                .toList());
        return result;
    }

    @Override
    public List<ProductDTO> getSuggestions(String query) {
        query = query.trim();
        List<ProductEntity> productList = productRepository.findByNameContainingIgnoreCase(query);
        return productList.stream()
                .map(item -> productDTOConverter.toProductDTO(item))
                .toList();
    }

    @Override
    public ResponseDTO save(String productJSON, MultipartFile[] images) throws JsonProcessingException {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            ProductDTO productDTO = objectMapper.readValue(productJSON, ProductDTO.class);
            ProductEntity productEntity = productDTOConverter.toProductEntity(productDTO);

            CategoryEntity categoryEntity = categoryRepository.getReferenceById(productDTO.getCategoryId());
            SupplierEntity supplierEntity = supplierRepository.getReferenceById(productDTO.getSupplierId());
            List<ColorEntity> colorEntityList = productDTO.getColors().stream()
                    .map(item -> colorRepository.findByName(item)
                            .orElseGet(() -> {
                                ColorEntity newColorEntity = colorDTOConverter.toColorEntity(item);
                                return colorRepository.save(newColorEntity); // Lưu mới
                            }))
                    .toList();

            List<SizeEntity> sizeEntityList = productDTO.getSizes().stream()
                    .map(item -> sizeRepository.findBySize(item.getSize())
                            .orElseGet(() -> {
                                SizeEntity newSizeEntity = sizeDTOConverter.toSizeEntity(item);
                                return sizeRepository.save(newSizeEntity); // Lưu mới
                            }))
                    .toList();

            productEntity.setCategoryEntity(categoryEntity);
            productEntity.setSupplierEntity(supplierEntity);
            productEntity.setColorList(colorEntityList);


            productEntity = productRepository.save(productEntity);

            ProductEntity finalProductEntity = productEntity;
            List<ProductSizeEntity> productSizeEntityList = productDTO.getSizes().stream()
                    .map(item -> {
                        ProductSizeEntity productSizeEntity = productSizeDTOConverter.toProductSizeEntity(
                                sizeRepository.findBySize(item.getSize()).get(),
                                item.getQuantity());
                        productSizeEntity.setProductEntity(finalProductEntity);
                        return productSizeEntity;
                    })
                    .toList();

            productSizeEntityList.forEach(productSizeRepository::save);

            List<ImageEntity> imageEntityList = this.saveImage(images);
            imageEntityList.forEach(image -> {
                image.setProductEntity(finalProductEntity);  // Set product cho Image
                imageRepository.save(image);
            });
            responseDTO.setMessage("Thêm sản phẩm thành công");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return responseDTO;
    }

    public List<ImageEntity> saveImage(MultipartFile[] images){
        List<ImageEntity> imageUrls = new ArrayList<>();
        List<String> publicIds = new ArrayList<>();
        try {
            for (MultipartFile file : images) {
                Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());

                String imageUrl = uploadResult.get("url").toString();
                String publicId = uploadResult.get("public_id").toString();

                publicIds.add(publicId);
                ImageEntity imageEntity = new ImageEntity();
                imageEntity.setURL(imageUrl);
                imageUrls.add(imageEntity);
                System.out.println("Đã upload thành công: " + imageUrl);
            }
        } catch (IOException e) {
            for (String publicId : publicIds) {
                try {
                    cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
                    System.out.println("Đã xóa ảnh với public_id: " + publicId);
                } catch (IOException ex) {
                    System.err.println("Không thể xóa ảnh với public_id: " + publicId);
                    ex.printStackTrace();
                }
            }
            throw new RuntimeException("Lỗi upload ảnh: " + e.getMessage(), e);
        }
        return imageUrls;
    }

    // -------------- Phuc --------------------
    @Override
    public Page<ProductEntity> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public Optional<ProductEntity> findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public Page<ProductEntity> findByNameContainingIgnoreCase(String namePattern, Pageable pageable) {
        return productRepository.findByNameContainingIgnoreCase(namePattern, pageable);
    }

    @Override
    public Page<ProductEntity> filterByMultipleCriteria(String namePattern, List<Long> supplierIds, List<Long> colorIds, List<Long> sizeIds, Pageable pageable) {
        // Convert empty lists or strings to null
        if (namePattern.isEmpty()) {
            namePattern = null;
        }
        if (supplierIds != null && supplierIds.isEmpty()) {
            supplierIds = null;
        }
        if (colorIds != null && colorIds.isEmpty()) {
            colorIds = null;
        }
        if (sizeIds != null && sizeIds.isEmpty()) {
            sizeIds = null;
        }
        return productRepository.filterByMultipleCriteria(namePattern, supplierIds, colorIds, sizeIds, pageable);
    }

    // -------------- Phuc --------------------


}
