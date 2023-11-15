package com.t3h.quan_ly_ban_hang.repository;

import com.t3h.quan_ly_ban_hang.entities.ProductSize;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;
@Transactional
public interface ProductSizeRepo extends JpaRepository<ProductSize, Long> {
    List<ProductSize> findAllByProductId(Long id);

    @Modifying
    void deleteAllByProductId(Long id);

    @Query(value = "select p.product_id from product_size p group by p.product_id order by sum(p.sold) desc limit 8;"
            , nativeQuery = true)
    List<Long> findProductIdBySold();
}
