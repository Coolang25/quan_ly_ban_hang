package com.t3h.quan_ly_ban_hang.entities;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Data
@Table(name = "bill")
public class Bill {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Basic
    @Column(name = "CREATED_TIME")
    private Timestamp createdTime;
    @Basic
    @Column(name = "STATUS")
    private int status;
    @Basic
    @Column(name = "USER_ID")
    private Long userId;
    @Basic
    @Column(name = "TOTAL_PRICE")
    private Integer totalPrice;
}
