package com.adidark.service;


import com.adidark.entity.RoleEntity;
import com.adidark.entity.UserEntity;

import com.adidark.entity.UserEntity;
import com.adidark.exception.PermissionDenyException;

import com.adidark.model.dto.ProductDTO;
import com.adidark.model.dto.RoleDTO;
import com.adidark.model.dto.SuperClassDTO;
import com.adidark.model.dto.UserDTO;

import com.adidark.model.response.ResponseDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface UserService {
    List<UserDTO> findAll(Pageable pageable);
    SuperClassDTO<UserDTO> searchUser(String query, Pageable pageable);
    UserEntity getUser(Long id);
    List<UserDTO> searchUser(String query);
    UserEntity createUser(UserDTO userDTO) throws PermissionDenyException;
    String login(String identifier, String password) throws Exception;
    ResponseDTO deleteCustomer(Long id);
    ResponseDTO updateCustomer(String productJSON) throws JsonProcessingException;
    UserEntity findByUserName(String userName);
    UserDTO getUserDTOFromToken();
    UserEntity updateUser(UserDTO userDTO) throws PermissionDenyException;
}
