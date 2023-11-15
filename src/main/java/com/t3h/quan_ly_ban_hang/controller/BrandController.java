package com.t3h.quan_ly_ban_hang.controller;

import com.t3h.quan_ly_ban_hang.dto.BrandDto;
import com.t3h.quan_ly_ban_hang.service.BrandService;
import com.t3h.quan_ly_ban_hang.service.CategoryService;
import com.t3h.quan_ly_ban_hang.utils.BrandSaveFileHelper;
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
@RequestMapping("/backend/brand")
public class BrandController {
    @Autowired
    CategoryService categoryService;
    @Autowired
    BrandService brandService;

    @RequestMapping(method = RequestMethod.GET, value = "")
    String listBrand(@RequestParam(name = "name", required = false) String name, Model model, @RequestParam(name = "id", required = false) String id, RedirectAttributes redirectAttributes){
        List<BrandDto> list = brandService.findAll(name);
        if (list.isEmpty()) {
            model.addAttribute("notFound", "Không tìm thấy thương hiệu");
        } else
            model.addAttribute("listBrand", list);

        if (id != null) {
            try {
                Long newId = Long.parseLong(id);
                BrandDto brandDto = brandService.get(newId);
                if (brandDto == null) {
                    redirectAttributes.addFlashAttribute("message", "Không tìm thấy sản phẩm có id = " + id);
                    return "redirect:/backend/brand/new";
                }
                else return "redirect:/backend/brand/" + id;
            } catch (NumberFormatException e) {
                redirectAttributes.addFlashAttribute("error", "Nhập lại ID hợp lệ");
                return "redirect:/backend/brand/new";
            }
        }
        return "/backend/brand/show.html";
    }

    @RequestMapping(method = RequestMethod.GET, value="/{id}")
    String detailBrand(@PathVariable Long id, Model model){
        Object brandDto = brandService.get(id);
        BrandDto newBrand = new BrandDto();
        model.addAttribute("brandById", brandDto);
        model.addAttribute("newBrand", newBrand);
        return "/backend/brand/create.html";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/new")
    String newPage(Model model) {
        BrandDto newBrand = new BrandDto();
        model.addAttribute("newBrand", newBrand);
        return "/backend/brand/create.html";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    String addBrand(@Valid @ModelAttribute BrandDto brandDto,
                       @RequestParam(value = "fileImage", required = false) MultipartFile imageMultipart,
                       BindingResult bindingResult,
                       Model model,
                       RedirectAttributes redirectAttributes) throws IOException {
        Object result = null;
        String msg = "";

        if (bindingResult.hasErrors()) return "/backend/brand/create.html";
        Long id = null;

        BrandSaveFileHelper.setImageName(imageMultipart, brandDto);

        if (brandDto.getId() == null) {
            result = brandService.create(brandDto);
            id = ((BrandDto) result).getId();
            msg = " Tạo mới";
        } else {
            id = brandDto.getId();
            result = brandService.create(brandDto);
            msg = "Cập nhật";
        }

        BrandSaveFileHelper.saveUploadedImages(imageMultipart, brandDto);

        if (Objects.equals(result, null)) {
            model.addAttribute("message", msg + " fail");
            return "/backend/brand/create.html";
        }
        redirectAttributes.addFlashAttribute("message", msg + " brand " + id + " thành công");
        return "redirect:/backend/brand/" + id;

    }
}
