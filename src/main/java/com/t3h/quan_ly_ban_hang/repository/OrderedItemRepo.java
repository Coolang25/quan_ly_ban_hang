package com.t3h.quan_ly_ban_hang.repository;

import com.t3h.quan_ly_ban_hang.entities.OrderedItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderedItemRepo extends JpaRepository<OrderedItem, Long> {
    List<OrderedItem> findAllByBillId(Long billId);
}
