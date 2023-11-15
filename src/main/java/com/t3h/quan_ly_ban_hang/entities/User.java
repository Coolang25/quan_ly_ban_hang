package com.t3h.quan_ly_ban_hang.entities;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
@Data
@Table(name = "user")
public class User implements UserDetails {
    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Basic
    @Column(name = "USERNAME")
    private String username;
    @Basic
    @Column(name = "PASSWORD")
    private String password;
    @Basic
    @Column(name = "FULL_NAME")
    private String fullName;
    @Basic
    @Column(name = "ADDRESS")
    private String address;
    @Basic
    @Column(name = "PHONE")
    private String phone;
    @Basic
    @Column(name = "EMAIL")
    private String email;
    @Basic
    @Column(name = "STATUS")
    private Boolean status;
    @Basic
    @Column(name = "SEX")
    private Integer sex;
    @Basic
    @Column(name = "ROLE")
    private String role;
    @Basic
    @Column(name = "AVATAR")
    private String avatar;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
        if (!StringUtils.isEmpty(this.role)) {
            grantedAuthorityList.add(new SimpleGrantedAuthority("ROLE_" + this.role));
        }
        return grantedAuthorityList;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.status;
    }

    public String getImagePath() {
        if (avatar == null)
            return "/images/image-thumbnail.png";

        return "/image-storage/user/" + this.id + "/" + this.avatar;
    }
}
