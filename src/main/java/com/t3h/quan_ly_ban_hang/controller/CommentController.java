package com.t3h.quan_ly_ban_hang.controller;

import com.t3h.quan_ly_ban_hang.dto.CommentDto;
import com.t3h.quan_ly_ban_hang.dto.ProductDto;
import com.t3h.quan_ly_ban_hang.dto.UserDto;
import com.t3h.quan_ly_ban_hang.service.CommentService;
import com.t3h.quan_ly_ban_hang.service.UserService;
import com.t3h.quan_ly_ban_hang.utils.ProductSaveFileHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

@Controller
@RequestMapping("/comment")
@PreAuthorize("isAuthenticated()")
public class CommentController {
    @Autowired
    UserService userService;
    @Autowired
    CommentService commentService;

    @RequestMapping(method = RequestMethod.POST, value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    String addProducts(@Valid @ModelAttribute CommentDto commentDto, Principal principal,
                       BindingResult bindingResult,
                       Model model,
                       RedirectAttributes redirectAttributes) throws IOException {
        Object result = null;
        String msg = "";

        if (bindingResult.hasErrors()) return "/frontend/detail.html";

        UserDto user = userService.findByUsername(principal.getName());
        commentDto.setUserId(user.getId());

        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        commentDto.setTime(timestamp);

        result = commentService.create(commentDto);

        if (Objects.equals(result, null)) {
            model.addAttribute("error", "Thêm bình luận thất bại");
            return "/backend/product/create.html";
        }
        return "redirect:/detail/" + commentDto.getProductId();

    }
}
