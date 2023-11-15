package com.t3h.quan_ly_ban_hang.service;

import com.t3h.quan_ly_ban_hang.entities.Brands;
import com.t3h.quan_ly_ban_hang.dto.BrandDto;
import com.t3h.quan_ly_ban_hang.repository.BrandRepo;
import com.t3h.quan_ly_ban_hang.repository.CategoryRepo;
import com.t3h.quan_ly_ban_hang.repository.ProductImageRepo;
import com.t3h.quan_ly_ban_hang.repository.ProductRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BrandService {
    @Autowired
    ProductRepo productRepo;
    @Autowired
    CategoryRepo categoryRepo;
    @Autowired
    BrandRepo brandRepo;
    @Autowired
    ProductImageRepo productImageRepo;
    @Autowired
    ModelMapper modelMapper;

    public List<BrandDto> findAll(String name) {
        List<Brands> list = new ArrayList<>();
        if(StringUtils.isEmpty(name))
            list = brandRepo.findAll();
        else
            list = brandRepo.findAllByNameContaining(name);
        return list.stream().map(brand -> modelMapper.map(brand, BrandDto.class)).collect(Collectors.toList());
    }

    public BrandDto get(Long id) {
        Brands brands = brandRepo.findById(id).orElse(null);
        BrandDto result = new BrandDto();
        if (brands == null) {
            return null;
        } else {
            BeanUtils.copyProperties(brands, result);
            return result;
        }
    }

    public BrandDto create(BrandDto brandDto) {
        Brands brands = new Brands();
        BeanUtils.copyProperties(brandDto, brands);
        brandRepo.save(brands);
        brandDto.setId(brands.getId());

        return brandDto;
    }

    public BrandDto update(Long id, BrandDto brandDto) {
        Brands brand = brandRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        BeanUtils.copyProperties(brandDto, brand);
        brandRepo.save(brand);
        return brandDto;
    }

    public void delete(Long id) {
        brandRepo.deleteById(id);
    }

}
