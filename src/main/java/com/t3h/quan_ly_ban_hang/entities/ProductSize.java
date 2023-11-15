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
@Table(name = "product_size")
public class ProductSize {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "SOLD")
    private Integer sold;

    @Basic
    @Column(name = "IN_STOCK")
    private Integer inStock;

    @Basic
    @Column(name = "PRODUCT_ID")
    private Long productId;

    @Basic
    @Column(name = "SIZE_ID")
    private Long sizeId;

}
