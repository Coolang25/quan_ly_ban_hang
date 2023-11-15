package com.t3h.quan_ly_ban_hang.entities;


import lombok.Data;
import javax.persistence.*;

import java.util.Objects;

@Entity
@Data
@Table(name = "brands")
public class Brands {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Basic
    @Column(name = "LOGO", nullable = false)
    private String logo;
    @Basic
    @Column(name = "NAME", nullable = false)
    private String name;
    @Basic
    @Column(name = "STATUS")
    private Boolean status;

}
