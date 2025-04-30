package com.youtube.jwt.sevice;

import com.youtube.jwt.configuration.JwtRequestFilter;
import com.youtube.jwt.dao.CartDao;
import com.youtube.jwt.dao.OrderDetailDao;
import com.youtube.jwt.dao.ProductDao;
import com.youtube.jwt.dao.UserDao;
import com.youtube.jwt.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderDetailService {
    private static final String  ORDER_PLACED = "Placed";

    @Autowired
    private OrderDetailDao orderDetailDao;

    @Autowired
    private ProductDao productDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private CartDao cartDao;

    public List<OrderDetail> placeOrder(OrderInput orderInput, boolean isSingleProductCheckout){
        List<OrderProductQuantity> productQuantityList = orderInput.getOrderProductQuantityList();

        List<OrderDetail> orderDetailList = new ArrayList<>();
        String currentUser = JwtRequestFilter.CURRENT_USER;
        User user = userDao.findById(currentUser).get();

        for(OrderProductQuantity o: productQuantityList){
            Product product = productDao.findById(o.getProductId()).get();
            OrderDetail orderDetail = new OrderDetail(
                orderInput.getFullName(),
                orderInput.getFullAddress(),
                orderInput.getContactNumber(),
                orderInput.getAlternateContactNumber(),
                ORDER_PLACED,
                product.getProductDiscountedPrice() * o.getQuantity(),
                product,
            user
            );
            orderDetailList.add(orderDetail);
        }
        orderDetailDao.saveAll(orderDetailList);
        //empty the cart
        if (!isSingleProductCheckout){
            List<Cart>carts = cartDao.findByUser(user);
            carts.stream().forEach(x -> cartDao.delete(x));
        }
        return orderDetailList;

    }
}
