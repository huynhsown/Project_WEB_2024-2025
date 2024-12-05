package com.adidark.api;

import com.adidark.model.dto.OrderDTO;
import com.adidark.model.response.ResponseDTO;
import com.adidark.service.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("v1/api")
@RestController
@Slf4j
public class OrderAPI {
    @Autowired
    private OrderService orderService;

    @GetMapping("/search-suggestions-order")
    public List<Object[]> searchSuggestionsOrder(String query){
        return orderService.searchOrder(query);
    }

    @PostMapping("/order/save")
    public ResponseDTO updateOrder(@RequestPart("order") String orderJson) throws JsonProcessingException{
        return orderService.updateOrder(orderJson);
    }
}
