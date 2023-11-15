package com.t3h.quan_ly_ban_hang.entities;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Data
@Table(name = "products")
public class Products {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "NAME", nullable = false)
    private String name;

    @Basic
    @Column(name = "COST", nullable = false)
    private Integer cost;

    @Basic
    @Column(name = "PRICE", nullable = false)
    private Integer price;

    @Basic
    @Column(name = "MAIN_IMAGE", nullable = false)
    private String mainImage;

    @Basic
    @Column(name = "SHORT_DESCRIPTION", nullable = false)
    private String shortDescription;

    @Basic
    @Column(name = "FULL_DESCRIPTION", nullable = false)
    private String fullDescription;

    @Basic
    @CreationTimestamp
    @Column(name = "CREATED_TIME")
    private Timestamp createdTime;

    @Basic
    @CreationTimestamp
    @Column(name = "UPDATED_TIME")
    private Timestamp updatedTime;

    @Basic
    @Column(name = "DISCOUNT_PERCENT")
    private Float discountPercent;

    @Basic
    @Column(name = "BRAND_ID", nullable = false)
    private Long brandId;

    @Basic
    @Column(name = "CATEGORY_ID", nullable = false)
    private Long categoryId;

    @Basic
    @Column(name = "PRICE_LEVEL", nullable = false)
    private String priceLevel;

    @Basic
    @Column(name = "STATUS")
    private Boolean status;

}
