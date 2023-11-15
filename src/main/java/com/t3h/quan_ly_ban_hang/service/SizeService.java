package com.t3h.quan_ly_ban_hang.service;

import com.t3h.quan_ly_ban_hang.dto.BrandDto;
import com.t3h.quan_ly_ban_hang.dto.SizeDto;
import com.t3h.quan_ly_ban_hang.entities.Brands;
import com.t3h.quan_ly_ban_hang.entities.Sizes;
import com.t3h.quan_ly_ban_hang.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SizeService {
    @Autowired
    ProductRepo productRepo;
    @Autowired
    CategoryRepo categoryRepo;
    @Autowired
    BrandRepo brandRepo;
    @Autowired
    ProductImageRepo productImageRepo;
    @Autowired
    SizeRepo sizeRepo;
    @Autowired
    ModelMapper modelMapper;

    public List<SizeDto> findAll(String name) {
        List<Sizes> list = new ArrayList<>();
        if(StringUtils.isEmpty(name))
            list = sizeRepo.findAll();
        else
            list = sizeRepo.findAllByNameContaining(name);
        return list.stream().map(size -> modelMapper.map(size, SizeDto.class)).collect(Collectors.toList());
    }

    public SizeDto get(Long id) {
        Sizes sizes = sizeRepo.findById(id).orElse(null);
        SizeDto result = new SizeDto();
        if (sizes == null) {
            return null;
        } else {
            BeanUtils.copyProperties(sizes, result);
            return result;
        }
    }

    public SizeDto create(SizeDto sizeDto) {
        Sizes sizes = new Sizes();
        BeanUtils.copyProperties(sizeDto, sizes);
        sizeRepo.save(sizes);
        sizeDto.setId(sizes.getId());

        return sizeDto;
    }

    public SizeDto update(Long id, SizeDto sizeDto) {
        Sizes sizes = sizeRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        BeanUtils.copyProperties(sizeDto, sizes);
        sizeRepo.save(sizes);
        return sizeDto;
    }

    public void delete(Long id) {
        sizeRepo.deleteById(id);
    }

}
