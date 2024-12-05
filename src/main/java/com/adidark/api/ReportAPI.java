package com.adidark.api;

import com.adidark.repository.OrderRepository;
import com.adidark.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("v1/api")
@RestController
public class ReportAPI {
    @Autowired
    OrderRepository orderRepository;

    @GetMapping("/total-income")
    public List<Object[]> getTotalIncomeByMonth(@RequestParam int year) {
        return orderRepository.getTotalIncomeByMonth(year);
    }

}
