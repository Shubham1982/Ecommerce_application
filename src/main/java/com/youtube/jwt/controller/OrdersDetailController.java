package com.youtube.jwt.controller;

import com.youtube.jwt.entity.OrderDetail;
import com.youtube.jwt.entity.OrderInput;
import com.youtube.jwt.entity.OrderProductQuantity;
import com.youtube.jwt.sevice.CartService;
import com.youtube.jwt.sevice.OrderDetailService;
import com.youtube.jwt.sevice.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrdersDetailController {

    @Autowired
    private OrderDetailService orderDetailService;

    @PreAuthorize("hasRole('User')")
    @PostMapping({"/placeOrder/{isCartCheckout}"})
    public List<OrderDetail> placeOrder(@PathVariable(name = "isSingleProductCheckout") boolean isSingleProductCheckout,
                                        @RequestBody OrderInput orderInput){
        return orderDetailService.placeOrder(orderInput,isSingleProductCheckout);
    }
    @GetMapping({"/getOrderDetails"})
    @PreAuthorize("hasRole('User')")
    public List<OrderDetail> getOrderDetails(){
        return orderDetailService.getOrderDetails();
    }

    @PreAuthorize("hasRole('Admin')")
    @GetMapping({"/getAllOrderDetails/{status}"})
    public List<OrderDetail> getAllOrderDetails(@PathVariable String status){
        return orderDetailService.getAllOrderDetails(status);
    }
    @PreAuthorize("hasRole('Admin')")
    @GetMapping({"/markOrderAsDelivered/{orderId}"})
    public String  markOrderAsDelivered(@PathVariable(name = "orderId")Integer orderId) {
        return orderDetailService.markOrderAsDelivered(orderId);
    }
}
