package com.t3h.quan_ly_ban_hang.repository;

import com.t3h.quan_ly_ban_hang.entities.Brands;
import com.t3h.quan_ly_ban_hang.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepo extends JpaRepository<User, Long> {
    List<User> findAllByFullNameContaining(String name);
    User findFirstByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByPhone(String phone);
    Boolean existsByEmail(String email);
}