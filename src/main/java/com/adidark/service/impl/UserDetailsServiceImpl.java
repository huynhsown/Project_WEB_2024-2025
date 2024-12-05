package com.adidark.service.impl;

import com.adidark.entity.UserEntity;
import com.adidark.service.UserDetailsService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Override
    public String getUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            String username = authentication.getName();
            Object ok = authentication.getDetails();
            return username;
        }
        return null;
    }

    @Override
    public Optional<UserEntity> getUserEntity() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return Optional.ofNullable(authentication)
                .map(auth -> {
                    UserEntity userEntity = (UserEntity) auth.getPrincipal();
                    userEntity.setPassWord(null);
                    return userEntity;
                });
    }
}
