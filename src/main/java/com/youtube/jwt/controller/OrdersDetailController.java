package com.youtube.jwt.controller;

import com.youtube.jwt.entity.OrderDetail;
import com.youtube.jwt.entity.OrderInput;
import com.youtube.jwt.entity.OrderProductQuantity;
import com.youtube.jwt.sevice.OrderDetailService;
import com.youtube.jwt.sevice.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrdersDetailController {

    @Autowired
    private OrderDetailService orderDetailService;

    @PreAuthorize("hasRole('User')")
    @PostMapping({"/placeOrder"})
    public List<OrderDetail> placeOrder(@RequestBody OrderInput orderInput){
        return orderDetailService.placeOrder(orderInput);
    }
}
