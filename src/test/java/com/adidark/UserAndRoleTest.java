package com.adidark;

import com.adidark.converter.ProductDTOConverter;
import com.adidark.converter.ProductSizeDTOConverter;
import com.adidark.converter.SupplierDTOConverter;
import com.adidark.entity.*;
import com.adidark.enums.RoleType;
import com.adidark.model.dto.ProductDTO;
import com.adidark.model.dto.SupplierDTO;
import com.adidark.repository.*;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.thymeleaf.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
public class UserAndRoleTest {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SizeRepository sizeRepository;

    @Autowired
    private ProductSizeRepository productSizeRepository;

    @Autowired
    private ProductDTOConverter productDTOConverter;

    @Autowired
    private ProductSizeDTOConverter productSizeDTOConverter;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private ColorRepository colorRepository;

    @Autowired
    private SupplierDTOConverter supplierDTOConverter;

    @Test
    @Transactional
    @Rollback(value = false)  // Mặc định là true, bạn có thể bỏ qua nếu không cần thử nghiệm thay đổi
    void oneToMany() {
        ProductSizeEntity productSizeEntity = productSizeDTOConverter.toProductSizeEntity(
                sizeRepository.findBySize(new BigDecimal("23.00")).get(),
                34
        );

        productSizeRepository.save(productSizeEntity);

        System.out.println(productSizeEntity);
    }
}
