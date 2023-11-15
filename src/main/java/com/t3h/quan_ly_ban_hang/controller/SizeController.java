package com.t3h.quan_ly_ban_hang.controller;

import com.t3h.quan_ly_ban_hang.dto.SizeDto;
import com.t3h.quan_ly_ban_hang.service.BrandService;
import com.t3h.quan_ly_ban_hang.service.CategoryService;
import com.t3h.quan_ly_ban_hang.service.SizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/backend/size")
public class SizeController {
    @Autowired
    CategoryService categoryService;
    @Autowired
    BrandService brandService;
    @Autowired
    SizeService sizeService;

    @RequestMapping(method = RequestMethod.GET, value = "")
    String listBrand(@RequestParam(name = "name", required = false) String name, Model model, @RequestParam(name = "id", required = false) String id, RedirectAttributes redirectAttributes){
        List<SizeDto> list = sizeService.findAll(name);
        SizeDto newSize = new SizeDto();
        model.addAttribute("newSize", newSize);
        model.addAttribute("listSize", list);

        if (id != null) {
            try {
                Long newId = Long.parseLong(id);
                SizeDto sizeDto = sizeService.get(newId);
                if (sizeDto == null) {
                    redirectAttributes.addFlashAttribute("message", "Không tìm thấy size có id = " + id);
                    return "redirect:/backend/size";
                }
                else return "redirect:/backend/size/" + id;
            } catch (NumberFormatException e) {
                redirectAttributes.addFlashAttribute("error", "Nhập lại ID hợp lệ");
                return "redirect:/backend/size";
            }
        }
        return "/backend/size/size.html";
    }

    @RequestMapping(method = RequestMethod.GET, value="/{id}")
    String detailBrand(@PathVariable Long id, Model model){
        List<SizeDto> list = sizeService.findAll("");
        SizeDto sizeDto = sizeService.get(id);
        SizeDto newSize = new SizeDto();
        model.addAttribute("sizeById", sizeDto);
        model.addAttribute("newSize", newSize);
        model.addAttribute("listSize", list);
        return "/backend/size/size.html";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    String addBrand(@Valid @ModelAttribute SizeDto sizeDto,
                       BindingResult bindingResult,
                       Model model,
                       RedirectAttributes redirectAttributes) throws IOException {
        Object result = null;
        String msg = "";

        if (bindingResult.hasErrors()) return "/backend/size/size.html";
        Long id = null;

        if (sizeDto.getId() == null) {
            result = sizeService.create(sizeDto);
            id = ((SizeDto) result).getId();
            msg = " Tạo mới";
        } else {
            id = sizeDto.getId();
            result = sizeService.create(sizeDto);
            msg = "Cập nhật";
        }

        if (Objects.equals(result, null)) {
            model.addAttribute("message", msg + " fail");
            return "/backend/size/size.html";
        }
        redirectAttributes.addFlashAttribute("message", msg + " size " + id + " thành công");
        return "redirect:/backend/size/" + id;

    }
}
