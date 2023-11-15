package com.t3h.quan_ly_ban_hang.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cart_item")
public class CartItem {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Basic
    @Column(name = "INTENDED_QUANTITY")
    private Integer intendedQuantity;
    @Basic
    @Column(name = "PRODUCT_SIZE_ID")
    private Long productSizeId;
    @Basic
    @Column(name = "USER_ID")
    private Long userId;
    @Basic
    @Column(name = "STATUS")
    private Boolean status;
}
