package com.adidark.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

@Service
public interface VNPAYService {

    String createOrder(HttpServletRequest request, int amount, String orderInfor, String urlReturn);

    int orderReturn(HttpServletRequest request);

}
