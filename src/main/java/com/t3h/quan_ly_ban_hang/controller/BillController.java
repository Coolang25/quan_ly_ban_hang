package com.t3h.quan_ly_ban_hang.controller;

import com.t3h.quan_ly_ban_hang.dto.*;
import com.t3h.quan_ly_ban_hang.service.*;
import com.t3h.quan_ly_ban_hang.utils.YourRequestObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/bill")
@PreAuthorize("isAuthenticated()")
public class BillController {
    @Autowired
    CategoryService categoryService;
    @Autowired
    BrandService brandService;
    @Autowired
    BillService billService;
    @Autowired
    CartItemService cartItemService;
    @Autowired
    UserService userService;

    @GetMapping(value = "")
    public String billHome(Model model, Principal principal) {
        UserDto user = userService.findByUsername(principal.getName());
        List<BillDto> billDtos = billService.findAllByUserId(user.getId());
        model.addAttribute("categories", categoryService.findAll(""));
        model.addAttribute("brands", brandService.findAll(""));
        model.addAttribute("billDtos", billDtos);
        return "/frontend/bill.html";
    }

    @PostMapping(value = "/save")
    String addBill(@RequestBody YourRequestObject requestObject, Principal principal,
                   Model model,
                   RedirectAttributes redirectAttributes) {
        List<Long> cartItemIDs = requestObject.getCartItemIDs();
        List<Integer> quantities = requestObject.getQuantities();
        Integer totalPrice = requestObject.getTotalPrice();
        UserDto user = userService.findByUsername(principal.getName());
        List<Long> productSizeIDs = new ArrayList<>();
        for (Long id : cartItemIDs) {
            productSizeIDs.add(cartItemService.get(id).getProductSizeId());
        }
        Object result = null;

        BillDto billDto = new BillDto();
        List<OrderedItemDto> orderedItemDtos = new ArrayList<>();

        for (int index = 0; index < cartItemIDs.size(); index++) {
            orderedItemDtos.add(new OrderedItemDto(quantities.get(index), cartItemIDs.get(index), productSizeIDs.get(index)));
        }

        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        billDto.setCreatedTime(timestamp);
        billDto.setStatus(1);
        billDto.setUserId(user.getId());
        billDto.setTotalPrice(totalPrice);
        billDto.setOrderedItemDtos(orderedItemDtos);

        result = billService.create(billDto);

        return "redirect:/bill";

    }
}
