package com.youtube.jwt.sevice;

import com.youtube.jwt.configuration.JwtRequestFilter;
import com.youtube.jwt.dao.CartDao;
import com.youtube.jwt.dao.ProductDao;
import com.youtube.jwt.dao.UserDao;
import com.youtube.jwt.entity.Cart;
import com.youtube.jwt.entity.Product;
import com.youtube.jwt.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService {

    @Autowired
    private CartDao cartDao;

    @Autowired
    private ProductDao productDao;
    @Autowired
    private UserDao userDao;
    public Cart addToCart(Integer productId){
        Product product = productDao.findById(productId).get();
        String username = getCurrentUser().getUserName();
        User user = null;
        if (username!=null){
            user = userDao.findById(username).get();
        }
        List<Cart>cartList = cartDao.findByUser(user);
        List <Cart>filteredList = cartList.stream().filter(x -> x.getProduct().getProductId() == productId).collect(Collectors.toList());
        if (filteredList.size()> 0){
            return null;
        }
        if(product != null && user!= null){
            Cart cart = new Cart(product,user);
            return cartDao.save(cart);
        }
        return null;
    }
    public List<Cart> getCartDetails(){
        User user = getCurrentUser();
        return cartDao.findByUser(user);
    }

    public String deleteCartItem(Integer cartId){

        String CurrentUser = getCurrentUser().getUserName().toLowerCase();

        Cart cart = cartDao.findById(cartId).get();
        String cartUser = cart.getUser().getUserName().toLowerCase();
        if(!CurrentUser.equals(cartUser)){
            throw new RuntimeException("Not a valid User");
        }
        cartDao.deleteById(cartId);
        return "product Item has been removed, Proudct :"+ cart.getProduct().getProductName();
    }

    public User getCurrentUser(){
        String username = JwtRequestFilter.CURRENT_USER;
        return userDao.findById(username).get();
    }
}
