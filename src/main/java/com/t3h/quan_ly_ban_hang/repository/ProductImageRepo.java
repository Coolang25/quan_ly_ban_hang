package com.t3h.quan_ly_ban_hang.repository;

import com.t3h.quan_ly_ban_hang.entities.ProductImages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import javax.transaction.Transactional;
import java.util.List;
@Transactional
public interface ProductImageRepo extends JpaRepository<ProductImages, Long> {
    List<ProductImages> findAllByProductId(Long id);

    @Modifying
    void deleteAllByProductId(Long id);
}
