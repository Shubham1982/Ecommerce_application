package com.youtube.jwt.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.youtube.jwt.entity.ImageModel;
import com.youtube.jwt.entity.Product;
import com.youtube.jwt.sevice.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.DataInput;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;


    @PostMapping(value = {"/addNewProduct"},consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public Product addNewProduct(@RequestPart("product") String products, @RequestPart("file")MultipartFile[] file){
//       return productService.addNewProduct(product);
        Product product = null;
        try{
            ObjectMapper mapper = new ObjectMapper();
            product = mapper.readValue( products, Product.class);

            Set<ImageModel> images = uploadImage(file);
            product.setProductImages(images);
            return productService.addNewProduct(product);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
    public Set<ImageModel> uploadImage(MultipartFile[] multipartFiles) throws IOException {
        Set<ImageModel> imageModels = new HashSet<>();
        for(MultipartFile file: multipartFiles){
            ImageModel imageModel = new ImageModel(
                    file.getOriginalFilename(),
                    file.getContentType(),
                    file.getBytes()
            );
            imageModels.add(imageModel);
        }
        return imageModels;
    }
    @PreAuthorize("hasRole('Admin')")
    @GetMapping({"/getAllProducts"})
    public List<Product> getAllProducts(@RequestParam(defaultValue = "0") int pageNumber,
                                        @RequestParam(defaultValue = "") String searchKey){
        return productService.getAllProducts(pageNumber,searchKey);
    }

    @PreAuthorize("hasRole('Admin')")
    @DeleteMapping({"/deleteProductDetails/{productId}"})
    public String deleteProductDetails(@PathVariable("productId")Integer productId){
        return productService.deleteProductDetails(productId);
    }

    @PreAuthorize("hasRole('Admin')")
    @GetMapping({"/getProductDetailsById/{productId}"})
    public Product getProductDetailsById(Integer productId){
        return productService.getProductDetailsById(productId).get();
    }

    @PreAuthorize("hasRole('User')")
    @GetMapping({"/getProductSingleProduct/{isSingleProductCheckout/{productId}"})
    public List<Product> getProductDetailsById(boolean isSingleProductCheckout, Integer productId){
        return productService.getProudctDetails(isSingleProductCheckout,productId);
    }


}
