package com.adidark;

import com.adidark.entity.RoleEntity;
import com.adidark.entity.UserEntity;
import com.adidark.enums.RoleType;
import com.adidark.repository.RoleRepository;
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
    private UserRepository userRepository;

    @Test
    @Transactional
    @Rollback(value = false)  // Mặc định là true, bạn có thể bỏ qua nếu không cần thử nghiệm thay đổi
    void oneToMany() {
        System.out.println(StringUtils.isEmptyOrWhitespace("  \n   "));
    }
}
