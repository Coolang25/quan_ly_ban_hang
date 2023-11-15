package com.t3h.quan_ly_ban_hang.service;

import com.t3h.quan_ly_ban_hang.entities.Categories;
import com.t3h.quan_ly_ban_hang.dto.CategoryDto;
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
public class CategoryService {
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

    public List<CategoryDto> findAll(String name) {
        List<Categories> list = new ArrayList<>();
        if(StringUtils.isEmpty(name))
            list = categoryRepo.findAll();
        else
            list = categoryRepo.findAllByNameContaining(name);
        return list.stream().map(category -> modelMapper.map(category, CategoryDto.class)).collect(Collectors.toList());
    }

    public CategoryDto get(Long id) {
        Categories categories = categoryRepo.findById(id).orElse(null);
        CategoryDto result = new CategoryDto();
        if (categories == null) {
            return null;
        } else {
            BeanUtils.copyProperties(categories, result);
            return result;
        }
    }

    public CategoryDto create(CategoryDto categoryDto) {
        Categories categories = new Categories();
        BeanUtils.copyProperties(categoryDto, categories);
        categoryRepo.save(categories);
        categoryDto.setId(categories.getId());

        return categoryDto;
    }

    public CategoryDto update(Long id, CategoryDto categoryDto) {
        Categories category = categoryRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        BeanUtils.copyProperties(categoryDto, category);
        categoryRepo.save(category);
        return categoryDto;
    }

    public void delete(Long id) {
        categoryRepo.deleteById(id);
    }

}
