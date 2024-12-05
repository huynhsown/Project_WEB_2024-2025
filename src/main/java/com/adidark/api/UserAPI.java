package com.adidark.api;


import com.adidark.entity.UserEntity;
import com.adidark.model.dto.OrderDTO;
import com.adidark.model.dto.UserDTO;
import com.adidark.model.response.ResponseDTO;
import com.adidark.model.dto.UserLoginDTO;
import com.adidark.service.RoleService;
import com.adidark.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.adidark.service.OrderService;
import com.adidark.service.UserService;
import com.adidark.service.VNPAYService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/v1/api")
@RestController
@Slf4j
public class UserAPI {
    @Autowired
    private UserService userService;
    private RoleService roleService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private VNPAYService vnPayService;

    @GetMapping("/search-suggestions-customer")
    public List<UserDTO> searchSuggestionsCustomer(String query){
        return userService.searchUser(query);
    }

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserDTO userDTO, BindingResult result){
        try {
            if(result.hasErrors()){
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            if(!userDTO.getPassword().equals(userDTO.getConfirmPassword())){
                return ResponseEntity.badRequest().body("Password not match");
            }
            UserEntity user = userService.createUser(userDTO);
            return ResponseEntity.ok("");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/customer/update")
    public ResponseEntity<?> updateAccountDetail(@Valid @RequestBody UserDTO userDTO, BindingResult result){
        try {
            if(result.hasErrors()){
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            if(!userDTO.getPassword().equals(userDTO.getConfirmPassword())){
                return ResponseEntity.badRequest().body("Password not match");
            }
            UserEntity user = userService.updateUser(userDTO);
            return ResponseEntity.ok("");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/customer/delete/{id}")
    public ResponseDTO deleteUser(@PathVariable(value = "id" ,required = true) Long id){
        return userService.deleteCustomer(id);
    }

    @PostMapping("/user/save")
    public ResponseDTO updateCustomer(@RequestPart("customer") String customerJson) throws JsonProcessingException {
        return userService.updateCustomer(customerJson);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserLoginDTO userLoginDTO, BindingResult result, HttpSession session){
        try{
            if(result.hasErrors()){
                List<String> errors = result.getFieldErrors()
                        .stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .toList();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
            }

            String token = userService.login(userLoginDTO.getIdentifier(), userLoginDTO.getPassword());
            session.setAttribute("authToken", token);
            return ResponseEntity.ok(Map.of("token", token));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/customer/order/{orderId}/payment")
    public ResponseEntity<Map<String, Object>> submitOrder(@PathVariable("orderId") Long orderId, HttpServletRequest request){
        OrderDTO orderDTO = orderService.findById(orderId);
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        String orderInfo = orderDTO.getId().toString();
        String vnpayUrl = vnPayService.createOrder(request, orderDTO.getTotalPrice().intValue(), orderInfo, baseUrl);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("vnpayUrl", vnpayUrl);

        return ResponseEntity.ok(response);
    }

}
