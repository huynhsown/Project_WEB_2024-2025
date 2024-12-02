package com.adidark.service.impl;

import com.adidark.converter.UserDTOConverter;
import com.adidark.entity.RoleEntity;
import com.adidark.entity.UserEntity;
import com.adidark.model.dto.SuperClassDTO;
import com.adidark.model.dto.UserDTO;
import com.adidark.model.response.ResponseDTO;
import com.adidark.repository.RoleRepository;
import com.adidark.repository.UserRepository;
import com.adidark.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import javax.swing.text.html.parser.Entity;
import java.util.List;
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

    @Override
    public List<UserDTO> findAll(Pageable pageable) {
        Page<UserEntity> userEntityPage= userRepository.findAll(pageable);
        return userEntityPage.stream().map(item -> userDTOConverter.toUserDTO(item)).toList();
    }


    @Override
    public SuperClassDTO<UserDTO> searchUser(String query, Pageable pageable) {
        Page<UserEntity> userList=null;
        if(!StringUtils.isEmpty(query)){
            if(query.matches("\\d+")){
                userList=userRepository.findByTelephoneContainingIgnoreCase(query,pageable);
            }
            else {
                userList=userRepository.findByFirstNameOrLastNameContainingIgnoreCase(query,pageable);

            }
        }
        else {
            userList=userRepository.findAll(pageable);
        }
        SuperClassDTO<UserDTO> userDTO=new SuperClassDTO<>();
        userDTO.setTotalPage(userList.getTotalPages());
        userDTO.setCurrentPage(pageable.getPageNumber());
        userDTO.setSearchValue(query);
        userDTO.setItems(userList.stream().map(item -> userDTOConverter.toUserDTO(item)).toList());
        return userDTO;
    }

    @Override
    public UserEntity getUser(Integer id) {
        return userRepository.findById(id);
    }

    @Override
    public List<UserDTO> searchUser(String query) {
        List<UserEntity> userList=null;
        if(query.matches("\\d+")){
            userList=userRepository.findByTelephoneContainingIgnoreCase(query);
        }
        else {
            userList=userRepository.findByFirstNameOrLastNameContainingIgnoreCase(query);

        }
        return userList.stream().map(item ->userDTOConverter.toUserDTO(item)).toList();
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        RoleEntity roleEntity = roleRepository.findById(userDTO.getId()).get();

        return null;
    }

    @Override
    public ResponseDTO deleteCustomer(Long id) {
        userRepository.deleteById(id);
        ResponseDTO responseDTO=new ResponseDTO();
        responseDTO.setMessage("Xóa khách hàng");
        return responseDTO;
    }

    @Override
    public ResponseDTO updateCustomer(String userJSON) throws JsonProcessingException {
        ResponseDTO responseDTO =new ResponseDTO();
        UserDTO userDTO=objectMapper.readValue(userJSON,UserDTO.class);

        try {
            RoleEntity roleEntity=roleRepository.findById(userDTO.getRoleId()).orElseThrow(()->new RuntimeException("Không tồn tại chức vụ này"));
            UserEntity userEntity=userRepository.findById(userDTO.getId()).orElseThrow(()->new RuntimeException("Không tồn tại khách hàng này"));

            userEntity.setRoleEntity(roleEntity);

            UserEntity saveUser=userRepository.saveAndFlush(userEntity);
            responseDTO.setMessage("Cập Nhật Thành Công");
        }
        catch (Exception e){
            responseDTO.setMessage("Lưu sản phẩm thất bại");
            throw new RuntimeException(e);
        }
        return responseDTO;
    }

}
