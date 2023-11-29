package com.t3h.quan_ly_ban_hang.service;

import com.t3h.quan_ly_ban_hang.dto.UserDto;
import com.t3h.quan_ly_ban_hang.entities.User;
import com.t3h.quan_ly_ban_hang.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {
    @Autowired
    ProductRepo productRepo;
    @Autowired
    CategoryRepo categoryRepo;
    @Autowired
    BrandRepo brandRepo;
    @Autowired
    UserRepo userRepo;
    @Autowired
    ProductImageRepo productImageRepo;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    PasswordEncoder passwordEncoder;

    public List<UserDto> findAll(String name) {
        List<User> list = new ArrayList<>();
        if(StringUtils.isEmpty(name))
            list = userRepo.findAll();
        else
            list = userRepo.findAllByFullNameContaining(name);
        return list.stream().map(user -> modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
    }

    public Boolean existsByUsername(String username) {
        return userRepo.existsByUsername(username);
    }

    public Boolean existsByPhone(String phone) {
        return userRepo.existsByPhone(phone);
    }

    public Boolean existsByEmail(String email) {
        return userRepo.existsByEmail(email);
    }

    public UserDto findByUsername(String username) {
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userRepo.findFirstByUsername(username), userDto);
        return userDto;
    }

    public UserDto get(Long id) {
        User user = userRepo.findById(id).orElse(null);
        UserDto result = new UserDto();
        if (user == null) {
            return null;
        } else {
            BeanUtils.copyProperties(user, result);
            result.setRePassword(result.getPassword());
            return result;
        }
    }

    public UserDto create(UserDto userDto) {
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User user = new User();
        BeanUtils.copyProperties(userDto, user);
        userRepo.save(user);
        userDto.setId(user.getId());

        return userDto;
    }

    public UserDto update(Long id, UserDto userDto) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        String passWord = user.getPassword();
        BeanUtils.copyProperties(userDto, user);
        userDto.setPassword(passWord);
        user.setPassword(passWord);
        userRepo.save(user);
        return userDto;
    }

    public UserDto updatePassword(Long id, String newPassWord) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        String passWord = passwordEncoder.encode(newPassWord);
        user.setPassword(passWord);
        userRepo.save(user);
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user, userDto);
        return userDto;
    }

    public void delete(Long id) {
        userRepo.deleteById(id);
    }

}
