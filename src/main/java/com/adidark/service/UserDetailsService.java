package com.adidark.service;

import com.adidark.entity.UserEntity;

import java.util.Optional;

public interface UserDetailsService {
    String getUserName();

    Optional<UserEntity> getUserEntity();
}
