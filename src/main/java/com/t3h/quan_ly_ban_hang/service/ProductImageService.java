package com.t3h.quan_ly_ban_hang.service;

import com.t3h.quan_ly_ban_hang.entities.ProductImages;
import com.t3h.quan_ly_ban_hang.repository.BrandRepo;
import com.t3h.quan_ly_ban_hang.repository.CategoryRepo;
import com.t3h.quan_ly_ban_hang.repository.ProductImageRepo;
import com.t3h.quan_ly_ban_hang.repository.ProductRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ProductImageService {
    @Autowired
    ProductRepo productRepo;
    @Autowired
    CategoryRepo categoryRepo;
    @Autowired
    BrandRepo brandRepo;
    @Autowired
    ProductImageRepo productImageRepo;

//    public List<ProductImages> findByProduct(Products products) {
//        return productImageRepo.findByProduct(products);
//    }

    public ProductImages get(Long id) {
        return productImageRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Long create(ProductImages productImages) {
        return productImageRepo.save(productImages).getId();
    }

    public void update(ProductImages productImageSource) {
        ProductImages productImage = productImageRepo.findById(productImageSource.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        BeanUtils.copyProperties(productImageSource, productImage);
        productImageRepo.save(productImage);
    }

    public void delete(Long id) {
        productImageRepo.deleteById(id);
    }

//    private ProductImages mapToEntity(ProductImageDto productImageDto) {
//        ProductImages productImages = new ProductImages();
//        productImages.setId(productImageDto.getId());
//        productImages.setName(productImageDto.getName());
//        productImages.setProduct(productRepo.findById(productImageDto.getProductId()).orElse(null));
//        return productImages;
//    }
}
