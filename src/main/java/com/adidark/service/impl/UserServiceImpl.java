package com.adidark.service.impl;

import com.adidark.converter.UserDTOConverter;
import com.adidark.entity.RoleEntity;
import com.adidark.entity.UserEntity;
import com.adidark.enums.RoleType;
import com.adidark.exception.DataNotFoundException;
import com.adidark.exception.PermissionDenyException;
import com.adidark.model.dto.SuperClassDTO;
import com.adidark.model.dto.UserDTO;
import com.adidark.model.response.ResponseDTO;
import com.adidark.repository.RoleRepository;
import com.adidark.repository.UserRepository;
import com.adidark.service.UserDetailsService;
import com.adidark.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.adidark.util.JwtTokenUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import javax.swing.text.html.parser.Entity;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserDTOConverter userDTOConverter;

    @Autowired
    private LocalContainerEntityManagerFactoryBean entityManagerFactory;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public List<UserDTO> findAll(Pageable pageable) {
        Page<UserEntity> userEntityPage = userRepository.findAll(pageable);
        return userEntityPage.stream().map(item -> userDTOConverter.toUserDTO(item)).toList();
    }


    @Override
    public SuperClassDTO<UserDTO> searchUser(String query, Pageable pageable) {
        Page<UserEntity> userList = null;
        if (!StringUtils.isEmpty(query)) {
            if (query.matches("\\d+")) {
                userList = userRepository.findByTelephoneContainingIgnoreCase(query, pageable);
            } else {
                userList = userRepository.findByFirstNameOrLastNameContainingIgnoreCase(query, pageable);

            }
        } else {
            userList = userRepository.findAll(pageable);
        }
        SuperClassDTO<UserDTO> userDTO = new SuperClassDTO<>();
        userDTO.setTotalPage(userList.getTotalPages());
        userDTO.setCurrentPage(pageable.getPageNumber());
        userDTO.setSearchValue(query);
        userDTO.setItems(userList.stream().map(item -> userDTOConverter.toUserDTO(item)).toList());
        return userDTO;
    }

    @Override
    public UserEntity getUser(Long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public List<UserDTO> searchUser(String query) {
        List<UserEntity> userList = null;
        if (query.matches("\\d+")) {
            userList = userRepository.findByTelephoneContainingIgnoreCase(query);
        } else {
            userList = userRepository.findByFirstNameOrLastNameContainingIgnoreCase(query);

        }
        return userList.stream().map(item -> userDTOConverter.toUserDTO(item)).toList();
    }

    public UserEntity createUser(UserDTO userDTO) throws PermissionDenyException {

        if (userRepository.existsByUserName(userDTO.getUserName())) {
            throw new DataIntegrityViolationException("Username already exists");
        }

        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new DataIntegrityViolationException("Email already exists");
        }

        if (userRepository.existsByTelephone(userDTO.getTelephone())) {
            throw new DataIntegrityViolationException("Phone number already exists");
        }

        RoleEntity roleEntity = roleRepository.findById(userDTO.getRoleId())
                .orElseThrow(() -> new DataNotFoundException("Role not found"));

        if (roleEntity.getRoleType().equals(RoleType.ADMIN)) {
            throw new PermissionDenyException("Can't regis an admin account");
        }

        UserEntity userEntity = UserEntity.builder()
                .userName(userDTO.getUserName())
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .passWord(userDTO.getPassword())
                .telephone(userDTO.getTelephone())
                .email(userDTO.getEmail())
                .roleEntity(roleEntity)
                .build();

        String passwordEncoded = passwordEncoder.encode(userDTO.getPassword());

        userEntity.setPassWord(passwordEncoded);

        return userRepository.save(userEntity);
    }

    @Override
    public ResponseDTO deleteCustomer(Long id) {
        userRepository.deleteById(id);
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("X�a kh�ch h�ng");
        return responseDTO;
    }

    @Override
    public String login(String identifier, String password) throws Exception {
        Optional<UserEntity> optionalUserEntity = userRepository.findByUserNameOrTelephoneOrEmail(identifier, identifier, identifier);
        if (optionalUserEntity.isEmpty()) {
            throw new DataNotFoundException("Invalid Username/Phone Number/ Email Or Password");
        }

        UserEntity existingUser = optionalUserEntity.get();

        if (!passwordEncoder.matches(password, existingUser.getPassword())) {
            throw new BadCredentialsException("Wrong Password");
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                identifier, password,
                existingUser.getAuthorities()
        );

        authenticationManager.authenticate(authenticationToken);
        return jwtTokenUtil.generateToken(existingUser);
    }

    @Override
    public UserEntity findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    @Override
    public UserDTO getUserDTOFromToken() {
        return userDTOConverter
                .toUserDTO(userDetailsService.getUserEntity()
                        .orElseThrow(() -> new DataNotFoundException("Token not found user")));
    }

    @Override
    public ResponseDTO updateCustomer(String userJSON) throws JsonProcessingException {
        ResponseDTO responseDTO = new ResponseDTO();
        UserDTO userDTO = objectMapper.readValue(userJSON, UserDTO.class);

        try {
            RoleEntity roleEntity = roleRepository.findById(userDTO.getRoleId()).orElseThrow(() -> new RuntimeException("Kh�ng t?n t?i ch?c v? n�y"));
            UserEntity userEntity = userRepository.findById(userDTO.getId()).orElseThrow(() -> new RuntimeException("Kh�ng t?n t?i kh�ch h�ng n�y"));

            userEntity.setRoleEntity(roleEntity);

            UserEntity saveUser = userRepository.saveAndFlush(userEntity);
            responseDTO.setMessage("C?p Nh?t Th�nh C�ng");
        } catch (Exception e) {
            responseDTO.setMessage("Luu s?n ph?m th?t b?i");
            throw new RuntimeException(e);
        }
        return responseDTO;
    }

}
