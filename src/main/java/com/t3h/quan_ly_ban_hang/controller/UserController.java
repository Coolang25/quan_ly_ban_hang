package com.t3h.quan_ly_ban_hang.controller;

import com.t3h.quan_ly_ban_hang.dto.UserDto;
import com.t3h.quan_ly_ban_hang.service.BrandService;
import com.t3h.quan_ly_ban_hang.service.CategoryService;
import com.t3h.quan_ly_ban_hang.service.UserService;
import com.t3h.quan_ly_ban_hang.utils.UserSaveFileHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/backend/user")
public class UserController {
    @Autowired
    CategoryService categoryService;
    @Autowired
    BrandService brandService;
    @Autowired
    UserService userService;

    @RequestMapping(method = RequestMethod.GET, value = "")
    String listUser(@RequestParam(name = "name", required = false) String name, Model model, @RequestParam(name = "id", required = false) String id, RedirectAttributes redirectAttributes){
        List<UserDto> list = userService.findAll(name);
        if (list.isEmpty()) {
            model.addAttribute("notFound", "Không tìm thấy người dùng");
        } else
            model.addAttribute("listUser", list);

        if (id != null) {
            try {
                Long newId = Long.parseLong(id);
                UserDto userDto = userService.get(newId);
                if (userDto == null) {
                    redirectAttributes.addFlashAttribute("message", "Không tìm thấy người dùng có id = " + id);
                    return "redirect:/backend/user/new";
                }
                else return "redirect:/backend/user/" + id;
            } catch (NumberFormatException e) {
                redirectAttributes.addFlashAttribute("error", "Nhập lại ID hợp lệ");
                return "redirect:/backend/user/new";
            }
        }
        return "/backend/user/show.html";
    }

    @RequestMapping(method = RequestMethod.GET, value="/{id}")
    String detailUser(@PathVariable Long id, Model model){
        Object userDto = userService.get(id);
        UserDto newUser = new UserDto();
        model.addAttribute("userDto", userDto);
        model.addAttribute("newUser", newUser);
        return "/backend/user/create.html";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/new")
    String newPage(Model model) {
        UserDto newUser = new UserDto();
        model.addAttribute("userDto", newUser);
        return "/backend/user/create.html";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    String addUser(@ModelAttribute @Valid UserDto userDto,
                       @RequestParam(value = "fileImage", required = false) MultipartFile imageMultipart,
                       BindingResult bindingResult,
                       Model model,
                       RedirectAttributes redirectAttributes) throws IOException {

        if (userDto.getId() == null) {
            if (!Objects.equals(userDto.getPassword(), userDto.getRePassword())){
                bindingResult.rejectValue("password", "error.userDto", "Mật khẩu không trùng khớp");
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
        }

        if (bindingResult.hasErrors()) {
            return "/backend/user/create.html";
        }

        Object result = null;
        String msg = "";
        Long id = null;

        UserSaveFileHelper.setAvatarName(imageMultipart, userDto);

        if (userDto.getId() == null) {
            result = userService.create(userDto);
            id = ((UserDto) result).getId();
            msg = " Tạo mới";
        } else {
            id = userDto.getId();
            result = userService.update(id, userDto);
            msg = "Cập nhật";
        }

        UserSaveFileHelper.saveUploadedAvatar(imageMultipart, userDto);

        if (Objects.equals(result, null)) {
            model.addAttribute("message", msg + " fail");
            return "/backend/user/create.html";
        }
        redirectAttributes.addFlashAttribute("message", msg + " user " + id + " thành công");
        return "redirect:/backend/user/" + id;

    }
}
