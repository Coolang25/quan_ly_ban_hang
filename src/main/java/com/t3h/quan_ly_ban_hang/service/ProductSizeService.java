package com.t3h.quan_ly_ban_hang.service;

import com.t3h.quan_ly_ban_hang.dto.ProductSizeDto;
import com.t3h.quan_ly_ban_hang.dto.UserDto;
import com.t3h.quan_ly_ban_hang.entities.ProductSize;
import com.t3h.quan_ly_ban_hang.entities.User;
import com.t3h.quan_ly_ban_hang.repository.ProductSizeRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProductSizeService {
    @Autowired
    ProductSizeRepo productSizeRepo;

    public ProductSizeDto get(Long id) {
        ProductSize productSize = productSizeRepo.findById(id).orElse(null);
        ProductSizeDto result = new ProductSizeDto();
        if (productSize == null) {
            return null;
        } else {
            BeanUtils.copyProperties(productSize, result);
            return result;
        }
    }
}
