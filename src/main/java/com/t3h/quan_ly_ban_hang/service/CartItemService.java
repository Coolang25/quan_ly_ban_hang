package com.t3h.quan_ly_ban_hang.service;

import com.t3h.quan_ly_ban_hang.dto.CartItemDto;
import com.t3h.quan_ly_ban_hang.dto.CategoryDto;
import com.t3h.quan_ly_ban_hang.dto.ProductDto;
import com.t3h.quan_ly_ban_hang.dto.ProductSizeDto;
import com.t3h.quan_ly_ban_hang.entities.CartItem;
import com.t3h.quan_ly_ban_hang.entities.ProductSize;
import com.t3h.quan_ly_ban_hang.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CartItemService {
    @Autowired
    ProductRepo productRepo;
    @Autowired
    CategoryRepo categoryRepo;
    @Autowired
    BrandRepo brandRepo;
    @Autowired
    CartItemRepo cartItemRepo;
    @Autowired
    SizeRepo sizeRepo;
    @Autowired
    ProductSizeRepo productSizeRepo;
    @Autowired
    ProductImageRepo productImageRepo;
    @Autowired
    ModelMapper modelMapper;

    public List<CartItemDto> findAll(String name) {
        List<CartItem> list = new ArrayList<>();
        if(StringUtils.isEmpty(name))
            list = cartItemRepo.findAll();
        return list.stream().map(cartItem -> modelMapper.map(cartItem, CartItemDto.class)).collect(Collectors.toList());
    }

    public List<CartItemDto> findAllByUserId(Long id) {
        List<CartItem> list = cartItemRepo.findAllByUserIdOrderByIdDesc(id);
        List<CartItemDto> result = new ArrayList<>();
        result = list.stream().map(cartItem -> modelMapper.map(cartItem, CartItemDto.class)).collect(Collectors.toList());
        for (CartItemDto cartItem: result) {
            ProductSize productSize = productSizeRepo.findById(cartItem.getProductSizeId()).orElse(null);
            cartItem.setProductDto(modelMapper.map(productRepo.findById(productSize.getProductId()), ProductDto.class));
            cartItem.setSizeName(sizeRepo.findById(productSize.getSizeId()).orElse(null).getName());
            cartItem.setInStock(productSize.getInStock());
        }
        return result;
    }

    public Boolean existsByUserIdAndProductSizeId(Long userId, Long productSizeId) {
        return cartItemRepo.existsByUserIdAndProductSizeId(userId, productSizeId);
    }

    public CartItemDto get(Long id) {
        CartItem cartItem = cartItemRepo.findById(id).orElse(null);
        CartItemDto result = new CartItemDto();
        if (cartItem == null) {
            return null;
        } else {
            BeanUtils.copyProperties(cartItem, result);
            return result;
        }
    }

    public CartItemDto create(CartItemDto cartItemDto) {
        CartItem cartItem = new CartItem();
        BeanUtils.copyProperties(cartItemDto, cartItem);
        cartItemRepo.save(cartItem);
        cartItemDto.setId(cartItem.getId());

        return cartItemDto;
    }

    public CartItemDto update(Long id, CartItemDto cartItemDto) {
        CartItem cartItem = cartItemRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        BeanUtils.copyProperties(cartItemDto, cartItem);
        cartItemRepo.save(cartItem);
        return cartItemDto;
    }

    public void delete(Long id) {
        cartItemRepo.deleteById(id);
    }

}
