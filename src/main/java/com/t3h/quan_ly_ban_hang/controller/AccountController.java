package com.t3h.quan_ly_ban_hang.controller;

import com.t3h.quan_ly_ban_hang.dto.UserDto;
import com.t3h.quan_ly_ban_hang.service.BrandService;
import com.t3h.quan_ly_ban_hang.service.CategoryService;
import com.t3h.quan_ly_ban_hang.service.UserService;
import com.t3h.quan_ly_ban_hang.utils.UserSaveFileHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Objects;

@Controller
public class AccountController {
    @Autowired
    UserService userService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    BrandService brandService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping(value = "login")
    public String login(Model model) {
        return "login.html";
    }

    @GetMapping(value = "register")
    public String register(Model model) {
        UserDto userDto = new UserDto();
        model.addAttribute("userDto", userDto);
        return "register.html";
    }

    @RequestMapping(method = RequestMethod.POST, value = "register/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    String addUser(@ModelAttribute @Valid UserDto userDto,
                   BindingResult bindingResult,
                   Model model,
                   RedirectAttributes redirectAttributes) throws IOException {

        if (!Objects.equals(userDto.getPassword(), userDto.getRePassword())){
            bindingResult.rejectValue("rePassword", "error.userDto", "Mật khẩu không trùng khớp");
        }
        if (userService.existsByUsername(userDto.getUsername())){
            bindingResult.rejectValue("username", "error.userDto", "Username đã tồn tại");
        }
        if (userService.existsByPhone(userDto.getPhone())){
            bindingResult.rejectValue("phone", "error.userDto", "Số điện thoại đã tồn tại");
        }
        if (userService.existsByEmail(userDto.getEmail())){
            bindingResult.rejectValue("email", "error.userDto", "Email đã tồn tại");
        }
        if (bindingResult.hasErrors()) {
            return "register.html";
        }

        Object result = null;
        String msg = "";
        Long id = null;

        if (userDto.getId() == null) {
            userDto.setStatus(true);
            userDto.setRole("USER");
            result = userService.create(userDto);
            id = ((UserDto) result).getId();
            msg = " Tạo mới";
        }

        if (Objects.equals(result, null)) {
            model.addAttribute("message", msg + " fail");
            return "register.html";
        }
        redirectAttributes.addFlashAttribute("message", msg + " user thành công");
        return "redirect:/register";

    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/account/{id}")
    public String accountInfomation(Model model, @PathVariable Long id) {
        UserDto userDto = userService.get(id);
        model.addAttribute("userDto", userDto);
        model.addAttribute("categories", categoryService.findAll(""));
        model.addAttribute("brands", brandService.findAll(""));
        return "/frontend/account.html";
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = RequestMethod.POST, value = "/account/{id}/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    String editAccount(@ModelAttribute @Valid UserDto userDto, @RequestParam(value = "fileImage", required = false) MultipartFile imageMultipart,
                   @PathVariable Long id,
                   BindingResult bindingResult,
                   Model model,
                   RedirectAttributes redirectAttributes) throws IOException {

        if (bindingResult.hasErrors()) {
            return "/frontend/account.html";
        }

        Object result = null;

        UserSaveFileHelper.setAvatarName(imageMultipart, userDto);
        result = userService.update(id, userDto);
        String msg = "Cập nhật";

        UserSaveFileHelper.saveUploadedAvatar(imageMultipart, userDto);

        if (Objects.equals(result, null)) {
            model.addAttribute("message", msg + " fail");
            return "/frontend/account.html";
        }
        redirectAttributes.addFlashAttribute("message", msg + " user thành công");
        return "redirect:/account/" + id;

    }

//    @RequestMapping(method = RequestMethod.POST, value = "/account/{id}/change", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    String editPassword(@ModelAttribute @Valid UserDto userDto, @RequestParam(value = "fileImage", required = false) MultipartFile imageMultipart,
//                       @PathVariable Long id,
//                       BindingResult bindingResult,
//                       Model model,
//                       RedirectAttributes redirectAttributes) throws IOException {
//
//
//        if (!Objects.equals(passwordEncoder.encode(userDto.getPassword()), userService.get(id).getPassword())){
//            bindingResult.rejectValue("password", "error.userDto", "Mật khẩu không đúng");
//        }
//        if (bindingResult.hasErrors()) {
//            return "/frontend/account.html";
//        }
//
//        Object result = null;
//
//        UserSaveFileHelper.setAvatarName(imageMultipart, userDto);
//
//        result = userService.updatePassword(id, userDto);
//        String msg = "Cập nhật";
//
//        UserSaveFileHelper.saveUploadedAvatar(imageMultipart, userDto);
//
//        if (Objects.equals(result, null)) {
//            model.addAttribute("message", msg + " fail");
//            return "/frontend/account.html";
//        }
//        redirectAttributes.addFlashAttribute("message", "Cập nhật mật khẩu thành công");
//        return "redirect:/account/" + id;
//
//    }

    @RequestMapping("/403")
    public String accessDenied() {
        return "errors/403";
    }
}
