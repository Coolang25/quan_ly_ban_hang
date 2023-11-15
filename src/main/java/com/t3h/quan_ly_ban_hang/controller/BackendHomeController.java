package com.t3h.quan_ly_ban_hang.controller;


import com.t3h.quan_ly_ban_hang.dto.BillDto;
import com.t3h.quan_ly_ban_hang.dto.CategoryDto;
import com.t3h.quan_ly_ban_hang.entities.Bill;
import com.t3h.quan_ly_ban_hang.service.BillService;
import com.t3h.quan_ly_ban_hang.utils.YourRequestObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;
import java.util.Objects;


@Controller
@RequestMapping("backend")
public class BackendHomeController {
    @Autowired
    BillService billService;

    @GetMapping(value = "")
    public String backendHome(Model model) {
        List<BillDto> billDtos = billService.findAllByStatusIn();
        model.addAttribute("billDtos", billDtos);
        return "backend/index.html";
    }

    @GetMapping(value = "/bill/{id}")
    public String updateBillStatus(Model model, @PathVariable Long id) {
        BillDto billDto = billService.get(id);
        model.addAttribute("billDto", billDto);
        return "backend/index.html";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/bill/{id}/save_status", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    String saveStatus(@ModelAttribute BillDto billDto, Model model, @PathVariable Long id, RedirectAttributes redirectAttributes) {
        BillDto result = null;
        result = billService.updateStatus(id, billDto);

        if (Objects.equals(result, null)) {
            model.addAttribute("message", "Cập nhật lỗi");
            return "/backend/index.html";
        }
        redirectAttributes.addFlashAttribute("message", "Cập nhật thành công");
        return "redirect:/backend/bill/" + id;
    }

}

