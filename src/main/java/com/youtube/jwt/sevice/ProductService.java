package com.youtube.jwt.sevice;

import com.youtube.jwt.configuration.JwtRequestFilter;
import com.youtube.jwt.dao.CartDao;
import com.youtube.jwt.dao.ProductDao;
import com.youtube.jwt.dao.UserDao;
import com.youtube.jwt.entity.Cart;
import com.youtube.jwt.entity.Product;
import com.youtube.jwt.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductDao productDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private CartDao cartDao;

    public Product addNewProduct(Product product) throws IOException {
        return productDao.save(product);
    }
    public List<Product> getAllProducts(int pageNumber, String searchKey){
        Pageable pageable = PageRequest.of(pageNumber,10);
        if (searchKey.equals("")){
            return productDao.findAll(pageable);
        }
        else{
            return productDao.findByProductNameContainingIgnoreCaseOrProductDescriptionContainingIgnoreCase(searchKey,searchKey,pageable);
        }

    }
    public String deleteProductDetails(Integer productId){
        productDao.deleteById(productId);
        return "successfully deleted recorder: "+ productId;
    }
    public Optional<Product> getProductDetailsById(Integer productId){
        return productDao.findById(productId);
    }

    public List<Product> getProudctDetails(boolean isSingleProductCheckout, Integer productId){

        if(isSingleProductCheckout && productId != 0){
            //we are going to but single product
            List<Product> list = new ArrayList<>();
            Product product = productDao.findById(productId).get();
            list.add(product);
            return list;
        }else {
            //we are going to checkout entire cart
            String username = JwtRequestFilter.CURRENT_USER;
            User user = userDao.findById(username).get();

            List<Cart> carts = cartDao.findByUser(user);

            return carts.stream().map(x-> x.getProduct()).collect(Collectors.toList());

        }
    }
}
