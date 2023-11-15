package com.t3h.quan_ly_ban_hang.repository;

import com.t3h.quan_ly_ban_hang.entities.Sizes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SizeRepo extends JpaRepository<Sizes, Long> {
    List<Sizes> findAllByNameContaining(String name);

//    @Query(value = "SELECT e.PRODUCT_ID FROM Sizes e GROUP BY e.PRODUCT_ID ORDER BY SUM(e.SOLD) DESC LIMIT 8", nativeQuery = true)
//        List<Integer> findProductsBySortedSold();

//    @Query(value = "SELECT * FROM Sizes e WHERE e.PRODUCT_ID = :id", nativeQuery = true)
//        List<Sizes> findSizesById(Integer id);

    //List<Sizes> findSizesByProduct(Products products);
}
