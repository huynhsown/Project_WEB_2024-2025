package com.adidark;

import com.adidark.converter.ProductDTOConverter;
import com.adidark.converter.SupplierDTOConverter;
import com.adidark.entity.ProductEntity;
import com.adidark.entity.RoleEntity;
import com.adidark.entity.SupplierEntity;
import com.adidark.entity.UserEntity;
import com.adidark.enums.RoleType;
import com.adidark.model.dto.ProductDTO;
import com.adidark.model.dto.SupplierDTO;
import com.adidark.repository.ProductRepository;
import com.adidark.repository.RoleRepository;
import com.adidark.repository.SupplierRepository;
import com.adidark.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.thymeleaf.util.StringUtils;

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
    private ProductDTOConverter productDTOConverter;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private SupplierDTOConverter supplierDTOConverter;

    @Test
    @Transactional
    @Rollback(value = false)  // Mặc định là true, bạn có thể bỏ qua nếu không cần thử nghiệm thay đổi
    void oneToMany() {
        SupplierEntity supplierEntity = supplierRepository.findById(1L).get();
        SupplierDTO supplierDTO = supplierDTOConverter.toSupplierDTO(supplierEntity);
    }
}
