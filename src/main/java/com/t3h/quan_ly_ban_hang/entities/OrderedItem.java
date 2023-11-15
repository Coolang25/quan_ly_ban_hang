package com.t3h.quan_ly_ban_hang.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Data
@Table(name = "ordered_item")
public class OrderedItem {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Basic
    @Column(name = "QUANTITY")
    private Integer quantity;
    @Basic
    @Column(name = "CART_ITEM_ID")
    private Long cartItemId;
    @Basic
    @Column(name = "PRODUCT_SIZE_ID")
    private Long productSizeId;
    @Basic
    @Column(name = "BILL_ID")
    private Long billId;

}
