package com.t3h.quan_ly_ban_hang.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Data
@Table(name = "sizes")
public class Sizes {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "NAME")
    private String name;
    @Basic
    @Column(name = "STATUS")
    private Boolean status;

}
