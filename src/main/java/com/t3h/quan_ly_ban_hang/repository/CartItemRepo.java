package com.t3h.quan_ly_ban_hang.repository;

import com.t3h.quan_ly_ban_hang.entities.Brands;
import com.t3h.quan_ly_ban_hang.entities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepo extends JpaRepository<CartItem, Long> {
    Boolean existsByUserIdAndProductSizeId(Long userId, Long productSizeId);
    List<CartItem> findAllByUserIdOrderByIdDesc(Long id);
}