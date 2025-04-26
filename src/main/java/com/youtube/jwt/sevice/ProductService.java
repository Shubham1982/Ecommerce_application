package com.youtube.jwt.sevice;

import com.youtube.jwt.dao.ProductDao;
import com.youtube.jwt.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductDao productDao;


    public Product addNewProduct(Product product) throws IOException {
        return productDao.save(product);
    }
    public List<Product> getAllProducts(){
        return (List<Product>) productDao.findAll();
    }
    public String deleteProductDetails(Integer productId){
        productDao.deleteById(productId);
        return "successfully deleted recorder: "+ productId;
    }
    public Optional<Product> getProductDetailsById(Integer productId){
        return productDao.findById  (productId);
    }


}
