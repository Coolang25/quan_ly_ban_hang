package com.t3h.quan_ly_ban_hang.controller;

import com.t3h.quan_ly_ban_hang.dto.*;
import com.t3h.quan_ly_ban_hang.entities.User;
import com.t3h.quan_ly_ban_hang.repository.UserRepo;
import com.t3h.quan_ly_ban_hang.service.*;
import com.t3h.quan_ly_ban_hang.utils.BrandSaveFileHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/cart")
@PreAuthorize("isAuthenticated()")
public class CartController {
    @Autowired
    CategoryService categoryService;
    @Autowired
    BrandService brandService;
    @Autowired
    UserService userService;
    @Autowired
    ProductSizeService productSizeService;
    @Autowired
    CartItemService cartItemService;

    @GetMapping(value = "")
    public String cartHome(Model model, Principal principal) {
        UserDto user = userService.findByUsername(principal.getName());
        List<CartItemDto> cartItems = cartItemService.findAllByUserId(user.getId());
        model.addAttribute("categories", categoryService.findAll(""));
        model.addAttribute("brands", brandService.findAll(""));
        model.addAttribute("cartItems", cartItems);
        return "/frontend/cart.html";
    }
    @RequestMapping(method = RequestMethod.GET, value="/{id}")
    String detailCartItem(@PathVariable Long id, Model model){
        CartItemDto cartItemDto = cartItemService.get(id);
        return "/frontend/cart.html";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/new")
    String newPage(Model model) {
        CartItemDto cartItemDto = new CartItemDto();
        model.addAttribute("newCartItem", cartItemDto);
        return "/frontend/cart.html";
    }


    @RequestMapping(method = RequestMethod.POST, value = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    String addCart(@Valid @ModelAttribute CartItemDto cartItemDto,
                    BindingResult bindingResult,
                    Principal principal,
                    Model model,
                    RedirectAttributes redirectAttributes) throws IOException {
        Object result = null;
        String msg = "";
        UserDto user = userService.findByUsername(principal.getName());
        cartItemDto.setUserId(user.getId());

        if (cartItemService.existsByUserIdAndProductSizeId(cartItemDto.getUserId(), cartItemDto.getProductSizeId())){
            redirectAttributes.addFlashAttribute("message", "Đã tồn tại sản phẩm trong giỏ hàng");
            return "redirect:/detail/" + productSizeService.get(cartItemDto.getProductSizeId()).getProductId();
        }
        if (bindingResult.hasErrors()) return "/frontend/detail.html";


        if (cartItemDto.getId() == null) {
            cartItemDto.setStatus(true);
            result = cartItemService.create(cartItemDto);
            msg = " Thêm vào giỏ hàng";
        } else {
            result = cartItemService.create(cartItemDto);
            msg = "Cập nhật";
        }


        if (Objects.equals(result, null)) {
            model.addAttribute("message", msg + " fail");
            return "/frontend/cart.html";
        }

        redirectAttributes.addFlashAttribute("message", msg + " thành công");
        return "redirect:/detail/" + productSizeService.get(cartItemDto.getProductSizeId()).getProductId();
    }

}
