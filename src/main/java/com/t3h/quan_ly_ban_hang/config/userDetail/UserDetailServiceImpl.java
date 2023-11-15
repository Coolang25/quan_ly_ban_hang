package com.t3h.quan_ly_ban_hang.config.userDetail;

import com.t3h.quan_ly_ban_hang.entities.User;
import com.t3h.quan_ly_ban_hang.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    UserRepo userRepo;
    @Autowired
    PasswordEncoder passwordEncoder;

    // Lấy dữ liệu từ database
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userEntity = userRepo.findFirstByUsername(username);
        if (userEntity == null) throw new UsernameNotFoundException("Không tìm thấy tài khoản");

        return userEntity;
//        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
//        if (!StringUtils.isEmpty(userEntity.getRole())) {
//            grantedAuthorityList.add(new SimpleGrantedAuthority("ROLE_" + userEntity.getRole()));
//        }
//        return new User(phone, userEntity.getPassword(), Objects.equals(userEntity.getStatus(), 1),
//                true, true, true, grantedAuthorityList);
    }
}
