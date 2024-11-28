package com.adidark.service.impl;

import com.adidark.converter.*;
import com.adidark.entity.*;
import com.adidark.model.dto.ProductDTO;
import com.adidark.model.dto.SuperClassDTO;
import com.adidark.model.response.ResponseDTO;
import com.adidark.repository.*;
import com.adidark.service.ProductService;
import com.adidark.util.ImageUtil;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Autowired
    private ImageUtil imageUtil;

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

    @Transactional
    @Override
    public ResponseDTO save(String productJSON, MultipartFile[] images) throws JsonProcessingException {
        ResponseDTO responseDTO = new ResponseDTO();
        ProductDTO productDTO = objectMapper.readValue(productJSON, ProductDTO.class);

        try {
            ProductEntity productEntity = productDTO.getId() != null
                    ? productRepository.findById(productDTO.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy sản phẩm"))
                    : productDTOConverter.toProductEntity(productDTO);

            CategoryEntity categoryEntity = categoryRepository.findById(productDTO.getCategoryId())
                    .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy danh mục"));

            SupplierEntity supplierEntity = supplierRepository.findById(productDTO.getSupplierId())
                    .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy nhà cung cấp"));

            List<ColorEntity> colorEntityList = productDTO.getColors().stream()
                    .map(color -> colorRepository.findByName(color)
                            .orElseGet(() -> colorRepository.save(colorDTOConverter.toColorEntity(color))))
                    .collect(Collectors.toList());

            List<SizeEntity> sizeEntityList = productDTO.getSizes().stream()
                    .map(size -> sizeRepository.findBySize(size.getSize())
                            .orElseGet(() -> sizeRepository.save(sizeDTOConverter.toSizeEntity(size))))
                    .collect(Collectors.toList());

            productEntity.setCategoryEntity(categoryEntity);
            productEntity.setSupplierEntity(supplierEntity);
            productEntity.setColorList(colorEntityList);

            ProductEntity savedProductEntity = productRepository.saveAndFlush(productEntity);

            List<ProductSizeEntity> productSizeEntityList = productDTO.getSizes().stream()
                    .map(item -> {
                        SizeEntity sizeEntity = sizeRepository.findBySize(item.getSize())
                                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy kích thước"));
                        ProductSizeEntity productSizeEntity = productSizeDTOConverter
                                .toProductSizeEntity(sizeEntity, item.getQuantity());
                        productSizeEntity.setProductEntity(savedProductEntity);
                        return productSizeEntity;
                    })
                    .collect(Collectors.toList());

            productSizeRepository.deleteByProductEntity(savedProductEntity);
            productSizeRepository.saveAll(productSizeEntityList);

            imageRepository.deleteByProductEntity(savedProductEntity);
            List<ImageEntity> imageEntityList = this.saveImage(images);
            imageEntityList.forEach(image -> {
                image.setProductEntity(savedProductEntity);
                imageRepository.save(image);
            });

            responseDTO.setMessage(productDTO.getId() != null
                    ? "Sửa sản phẩm thành công"
                    : "Thêm sản phẩm thành công");

        } catch (Exception e) {
            responseDTO.setMessage("Lưu sản phẩm thất bại");
            throw new RuntimeException(e);
        }
        return responseDTO;
    }

    @Override
    public ProductDTO findProductById(Long id) {
        Optional<ProductEntity> productEntity = this.findById(id);
        if(productEntity.isEmpty()) return null;

        ProductDTO productDTO = productDTOConverter.toProductDTO(productEntity.get());

        System.out.println(productDTO);

        return productDTO;
    }

    @Override
    public ResponseDTO deleteById(Long id) {
        ResponseDTO responseDTO = new ResponseDTO();
        productRepository.deleteById(id);
        responseDTO.setMessage("Xóa sản phẩm thành công");
        return responseDTO;
    }

    public List<ImageEntity> saveImage(MultipartFile[] images) throws IOException {
        List<ImageEntity> imageUrls = new ArrayList<>();
        for(MultipartFile image: images){
            ImageEntity imageEntity = new ImageEntity();
            imageEntity.setURL(imageUtil.encodeImageToBase64(image));
            imageUrls.add(imageEntity);
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
