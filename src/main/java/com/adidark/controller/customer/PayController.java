package com.adidark.controller.customer;

import com.adidark.model.dto.PaymentDTO;
import com.adidark.service.PaymentService;
import com.adidark.service.VNPAYService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Controller
public class PayController {

    @Autowired
    private VNPAYService vnPayService;

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/vnpay-payment-return")
    public String paymentCompleted(HttpServletRequest request, Model model) {
        int paymentStatus = vnPayService.orderReturn(request);

        String orderInfo = request.getParameter("vnp_OrderInfo");
        String totalPrice = request.getParameter("vnp_Amount");
        String paymentType = request.getParameter("vnp_CardType");

        BigDecimal amount = new BigDecimal(totalPrice).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
        PaymentDTO paymentDTO = new PaymentDTO(amount, paymentType);
        paymentService.createPayment(Long.parseLong(orderInfo), paymentDTO);
        return paymentStatus == 1 ? "customer/orderSuccess" : "customer/orderFail";
    }

}
