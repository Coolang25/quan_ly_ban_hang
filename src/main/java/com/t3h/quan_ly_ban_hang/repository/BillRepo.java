package com.t3h.quan_ly_ban_hang.repository;

import com.t3h.quan_ly_ban_hang.entities.Bill;
import com.t3h.quan_ly_ban_hang.entities.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BillRepo extends JpaRepository<Bill, Long> {
    List<Bill> findAllByUserIdOrderByCreatedTimeDesc(Long userId);

    List<Bill> findAllByStatusIn(List<Integer> statuses);

    List<Bill> findAllByStatusOrderByCreatedTimeDesc(int status);

//    @Query(value = "select * from bill as b where DATE(b.created_time) = CURDATE() and b.status = 3", nativeQuery = true)
//        List<Bill> findAllSoldDuringTheDay();
//
//    @Query(value = "select * from bill as b where MONTH(b.created_time) = MONTH(CURDATE()) AND YEAR(b.created_time) = YEAR(CURDATE()) and b.status = 3", nativeQuery = true)
//    List<Bill> findAllSoldDuringTheMonth();
//
//    @Query(value = "select * from bill as b where YEAR(time) = YEAR(CURDATE()) and b.status = 3", nativeQuery = true)
//    List<Bill> findAllSoldDuringTheYear();
}
