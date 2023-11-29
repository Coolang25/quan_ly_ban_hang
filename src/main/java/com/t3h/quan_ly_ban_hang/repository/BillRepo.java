package com.t3h.quan_ly_ban_hang.repository;

import com.t3h.quan_ly_ban_hang.entities.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BillRepo extends JpaRepository<Bill, Long> {
    List<Bill> findAllByUserIdOrderByCreatedTimeDesc(Long userId);

    List<Bill> findAllByStatusIn(List<Integer> statuses);

    List<Bill> findAllByStatusOrderByCreatedTimeDesc(int status);
}
