package com.t3h.quan_ly_ban_hang.service;

import com.t3h.quan_ly_ban_hang.dto.*;
import com.t3h.quan_ly_ban_hang.entities.*;
import com.t3h.quan_ly_ban_hang.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BillService {
    @Autowired
    BillRepo billRepo;
    @Autowired
    ProductRepo productRepo;
    @Autowired
    SizeRepo sizeRepo;
    @Autowired
    ProductSizeRepo productSizeRepo;
    @Autowired
    OrderedItemRepo orderedItemRepo;
    @Autowired
    ModelMapper modelMapper;

    public List<BillDto> findAllByUserId (Long userId) {
        List<Bill> list = billRepo.findAllByUserIdOrderByCreatedTimeDesc(userId);
        List<BillDto> result = new ArrayList<>();
        if(list != null) {
            for (Bill bill : list) {
                BillDto billDto = new BillDto();
                BeanUtils.copyProperties(bill, billDto);

                List<OrderedItem> orderedItems = orderedItemRepo.findAllByBillId(billDto.getId());
                List<OrderedItemDto> orderedItemDtos = orderedItems.stream().map(orderedItem -> modelMapper.map(orderedItem, OrderedItemDto.class)).collect(Collectors.toList());

                for (OrderedItemDto orderedItemDto: orderedItemDtos) {
                    ProductSize productSize = productSizeRepo.findById(orderedItemDto.getProductSizeId()).orElse(null);
                    orderedItemDto.setProductDto(modelMapper.map(productRepo.findById(productSize.getProductId()), ProductDto.class));
                    orderedItemDto.setSizeName(sizeRepo.findById(productSize.getSizeId()).orElse(null).getName());
                }
                billDto.setOrderedItemDtos(orderedItemDtos);
                result.add(billDto);
            }
        }
        return result;
    }

    public BillDto get(Long id) {
        Bill bill = billRepo.findById(id).orElse(null);
        BillDto billDto = new BillDto();
        if (bill == null) {
            return null;
        } else {
            BeanUtils.copyProperties(bill, billDto);

            List<OrderedItem> orderedItems = orderedItemRepo.findAllByBillId(billDto.getId());
            List<OrderedItemDto> orderedItemDtos = orderedItems.stream().map(orderedItem -> modelMapper.map(orderedItem, OrderedItemDto.class)).collect(Collectors.toList());
            for (OrderedItemDto orderedItemDto: orderedItemDtos) {
                ProductSize productSize = productSizeRepo.findById(orderedItemDto.getProductSizeId()).orElse(null);
                orderedItemDto.setProductDto(modelMapper.map(productRepo.findById(productSize.getProductId()), ProductDto.class));
                orderedItemDto.setSizeName(sizeRepo.findById(productSize.getSizeId()).orElse(null).getName());
            }

            billDto.setOrderedItemDtos(orderedItemDtos);
            return billDto;
        }
    }

    public BillDto create(BillDto billDto) {
        Bill bill = new Bill();
        BeanUtils.copyProperties(billDto, bill);

        billRepo.save(bill);
        billDto.setId(bill.getId());

        if (!CollectionUtils.isEmpty(billDto.getOrderedItemDtos())) {
            List<OrderedItemDto> orderedItemDtos = billDto.getOrderedItemDtos();
            for (OrderedItemDto orderedItemDto: orderedItemDtos) {
                orderedItemDto.setBillId(billDto.getId());
                ProductSize productSize = productSizeRepo.findById(orderedItemDto.getProductSizeId()).orElse(null);
                productSize.setInStock(productSize.getInStock() - orderedItemDto.getQuantity());
                productSizeRepo.save(productSize);
            }
            orderedItemRepo.saveAll(orderedItemDtos.stream().map(orderedItemDto -> modelMapper.map(orderedItemDto, OrderedItem.class)).collect(Collectors.toList()));
        }

        return billDto;
    }

    public List<BillDto> findAllByStatusIn() {
        List<Integer>  statuses = Arrays.asList(1, 2);
        List<Bill> bills = billRepo.findAllByStatusIn(statuses);
        List<BillDto> billDtos = bills.stream().map(bill -> modelMapper.map(bill, BillDto.class)).collect(Collectors.toList());

        for (BillDto billDto : billDtos) {
            List<OrderedItemDto> orderedItemDtos = orderedItemRepo.findAllByBillId(billDto.getId()).stream().map(orderedItem -> modelMapper.map(orderedItem, OrderedItemDto.class)).collect(Collectors.toList());
            for (OrderedItemDto orderedItemDto: orderedItemDtos) {
                ProductSize productSize = productSizeRepo.findById(orderedItemDto.getProductSizeId()).orElse(null);
                orderedItemDto.setProductDto(modelMapper.map(productRepo.findById(productSize.getProductId()), ProductDto.class));
                orderedItemDto.setSizeName(sizeRepo.findById(productSize.getSizeId()).orElse(null).getName());
            }
            billDto.setOrderedItemDtos(orderedItemDtos);
        }

        return billDtos;
    }

    public BillDto updateStatus(Long id, BillDto billDto) {
        Bill bill = billRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        int status = billDto.getStatus();
        bill.setStatus(status);
        billRepo.save(bill);

        //Trả lại số lượng sản phẩm về kho
        if (status == 0) {
            List<OrderedItem> orderedItems = orderedItemRepo.findAllByBillId(bill.getId());
            for (OrderedItem orderedItem : orderedItems) {
                ProductSize productSize = productSizeRepo.findById(orderedItem.getProductSizeId()).orElse(null);
                productSize.setInStock(productSize.getInStock() + orderedItem.getQuantity());
                productSizeRepo.save(productSize);
            }
        }
        return billDto;
    }

//    public BrandDto update(Long id, BrandDto brandDto) {
//        Brands brand = brandRepo.findById(id)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
//        BeanUtils.copyProperties(brandDto, brand);
//        brandRepo.save(brand);
//        return brandDto;
//    }
//
//    public void delete(Long id) {
//        brandRepo.deleteById(id);
//    }

}
