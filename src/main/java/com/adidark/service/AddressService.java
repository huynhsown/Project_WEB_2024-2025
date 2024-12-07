package com.adidark.service;

import com.adidark.entity.AddressEntity;
import org.springframework.stereotype.Service;

@Service
public interface AddressService {
    AddressService save(AddressEntity addressEntity);
    AddressEntity save(String addressName);
}
