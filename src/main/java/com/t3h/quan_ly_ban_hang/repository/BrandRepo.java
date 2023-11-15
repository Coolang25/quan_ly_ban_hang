package com.t3h.quan_ly_ban_hang.repository;

import com.t3h.quan_ly_ban_hang.entities.Brands;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BrandRepo extends JpaRepository<Brands, Long> {
    List<Brands> findAllByNameContaining(String name);
}
