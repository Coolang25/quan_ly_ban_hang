package com.t3h.quan_ly_ban_hang.controller;

import com.t3h.quan_ly_ban_hang.dto.CategoryDto;
import com.t3h.quan_ly_ban_hang.service.CategoryService;
import com.t3h.quan_ly_ban_hang.utils.CategorySaveFileHelper;
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
@RequestMapping("/backend/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @RequestMapping(method = RequestMethod.GET, value = "")
    String listCategory(@RequestParam(name = "name", required = false) String name, Model model, @RequestParam(name = "id", required = false) String id, RedirectAttributes redirectAttributes){
        List<CategoryDto> list = categoryService.findAll(name);
        model.addAttribute("listCategory", list);
        if (list.isEmpty()) {
            model.addAttribute("notFound", "Không tìm thấy thương hiệu");
        } else
            model.addAttribute("listCategory", list);

        if (id != null) {
            try {
                Long newId = Long.parseLong(id);
                CategoryDto categoryDto = categoryService.get(newId);
                if (categoryDto == null) {
                    redirectAttributes.addFlashAttribute("message", "Không tìm thấy sản phẩm có id = " + id);
                    return "redirect:/backend/category/new";
                }
                else return "redirect:/backend/category/" + id;
            } catch (NumberFormatException e) {
                redirectAttributes.addFlashAttribute("error", "Nhập lại ID hợp lệ");
                return "redirect:/backend/category/new";
            }
        }
        return "/backend/category/show.html";
    }

    @RequestMapping(method = RequestMethod.GET, value="/{id}")
    String detailCategory(@PathVariable Long id, Model model){
        Object c = categoryService.get(id);
        CategoryDto newCategory = new CategoryDto();
        model.addAttribute("categoryById", c);
        model.addAttribute("newCategory", newCategory);
        return "/backend/category/create.html";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/new")
    String newPage(Model model) {
        CategoryDto newCategory = new CategoryDto();
        model.addAttribute("newCategory", newCategory);
        return "/backend/category/create.html";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    String addCategory(@Valid @ModelAttribute CategoryDto categoryDto,
                       @RequestParam(value = "fileImage", required = false) MultipartFile imageMultipart,
                       BindingResult bindingResult,
                       Model model,
                       RedirectAttributes redirectAttributes) throws IOException {
        Object result = null;
        String msg = "";

        if (bindingResult.hasErrors()) return "/backend/category/create.html";
        Long id = null;

        CategorySaveFileHelper.setImageName(imageMultipart, categoryDto);

        if (categoryDto.getId() == null) {
            result = categoryService.create(categoryDto);
            id = ((CategoryDto) result).getId();
            msg = " Tạo mới";
        } else {
            id = categoryDto.getId();
            result = categoryService.create(categoryDto);
            msg = "Cập nhật";
        }

        CategorySaveFileHelper.saveUploadedImages(imageMultipart, categoryDto);

        if (Objects.equals(result, null)) {
            model.addAttribute("message", msg + " fail");
            return "/backend/category/create.html";
        }
        redirectAttributes.addFlashAttribute("message", msg + " category " + id + " thành công");
        return "redirect:/backend/category/" + id;

    }

}
