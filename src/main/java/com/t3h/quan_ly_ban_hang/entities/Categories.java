package com.t3h.quan_ly_ban_hang.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;
import java.util.Optional;

@Entity
@Data
@Table(name = "categories")
public class Categories {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "IMAGE", nullable = false)
    private String image;

    @Basic
    @Column(name = "NAME", nullable = false)
    private String name;
    @Basic
    @Column(name = "STATUS")
    private Boolean status;

}
