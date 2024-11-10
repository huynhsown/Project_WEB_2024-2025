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
        // 1. Tạo đối tượng RoleEntity với RoleType.ADMIN
        RoleEntity admin = new RoleEntity();
        admin.setRoleType(RoleType.CUSTOMER);

        // 2. Tạo đối tượng UserEntity
        UserEntity user1 = new UserEntity();
        user1.setUserName("us1er1");
        user1.setPassWord("password1");
        user1.setTelephone("12345162789");
        user1.setFirstName("First1");
        user1.setLastName("Last1");
        user1.setEmail("use2r1@example.com");
        user1.setRoleEntity(admin);

        UserEntity user2 = new UserEntity();
        user2.setUserName("user2");
        user2.setPassWord("password2");
        user2.setTelephone("1234567890");
        user2.setFirstName("First2");
        user2.setLastName("Last2");
        user2.setEmail("user2@example.com");
        user2.setRoleEntity(admin);

        UserEntity user3 = new UserEntity();
        user3.setUserName("user3");
        user3.setPassWord("password3");
        user3.setTelephone("0987654321");
        user3.setFirstName("First3");
        user3.setLastName("Last3");
        user3.setEmail("user3@example.com");
        user3.setRoleEntity(admin);

        UserEntity user4 = new UserEntity();
        user4.setUserName("user4");
        user4.setPassWord("password4");
        user4.setTelephone("1122334455");
        user4.setFirstName("First4");
        user4.setLastName("Last4");
        user4.setEmail("user4@example.com");
        user4.setRoleEntity(admin);

        UserEntity user5 = new UserEntity();
        user5.setUserName("user5");
        user5.setPassWord("password5");
        user5.setTelephone("9988776655");
        user5.setFirstName("First5");
        user5.setLastName("Last5");
        user5.setEmail("user5@example.com");
        user5.setRoleEntity(admin);


        // 3. Gán người dùng cho role
        admin.setUserList(List.of(user1, user2, user3, user4, user5));

        // 4. Lưu RoleEntity và UserEntity
        roleRepository.save(admin);  // Chỉ cần lưu RoleEntity, Hibernate sẽ tự động lưu UserEntity
    }
}
