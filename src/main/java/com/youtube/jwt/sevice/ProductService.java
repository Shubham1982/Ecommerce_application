package com.youtube.jwt.sevice;

import com.youtube.jwt.dao.ProductDao;
import com.youtube.jwt.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductDao productDao;


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
        if(isSingleProductCheckout){
            //we are going to but single product
            List<Product> list = new ArrayList<>();
            Product product = productDao.findById(productId).get();
            list.add(product);
            return list;
        }else {
            //we are goint to checkout entire cart
        }
        return new ArrayList<>();
    }
}
