package com.t3h.quan_ly_ban_hang.controller;

import com.t3h.quan_ly_ban_hang.dto.ProductDto;
import com.t3h.quan_ly_ban_hang.service.BrandService;
import com.t3h.quan_ly_ban_hang.service.CategoryService;
import com.t3h.quan_ly_ban_hang.service.ProductService;
import com.t3h.quan_ly_ban_hang.service.SizeService;
import com.t3h.quan_ly_ban_hang.utils.ProductSaveFileHelper;
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
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/backend/product")
public class ProductController {

    @Autowired
    ProductService productsService;
    @Autowired
    BrandService brandService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    SizeService sizeService;

    @RequestMapping(method = RequestMethod.GET, value = "")
    String listProducts(Model model, @RequestParam(required = false) String name, @RequestParam(name = "id", required = false) String id, RedirectAttributes redirectAttributes) {
        List<ProductDto> productDto = productsService.findProductByName(name);
        if (productDto.isEmpty()) {
            model.addAttribute("notFound", "Không tìm thấy sản phẩm");
        } else
            model.addAttribute("listProduct", productDto);
        if (id != null) {
            try {
                Long newId = Long.parseLong(id);
                ProductDto productDto1 = productsService.get(newId);
                if (productDto1 == null) {
                    redirectAttributes.addFlashAttribute("notFound", "Không tìm thấy sản phẩm có id = " + id);
                    return "redirect:/backend/product/new";
                }
                else return "redirect:/backend/product/" + id;
            } catch (NumberFormatException e) {
                redirectAttributes.addFlashAttribute("error", "Nhập lại ID hợp lệ");
                return "redirect:/backend/product/new";
            }
        }
        return "/backend/product/show.html";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/new")
    String newPage(Model model) {
        ProductDto p = new ProductDto();
        model.addAttribute("productsDto", p);
        model.addAttribute("listBrands", brandService.findAll(null));
        model.addAttribute("listCategories", categoryService.findAll(null));
        model.addAttribute("listSizes", sizeService.findAll(""));
        model.addAttribute("numberOfExistingExtraImages", 0);
        return "/backend/product/create.html";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    String detailProducts(@PathVariable Long id, Model model) {
        ProductDto p = productsService.get(id);
        //List<Sizes> s = new ArrayList<>()
        model.addAttribute("productsDto", p);
        model.addAttribute("listBrands", brandService.findAll(null));
        model.addAttribute("listCategories", categoryService.findAll(null));
        model.addAttribute("listSizes", sizeService.findAll(""));
        model.addAttribute("numberOfExistingExtraImages", 0);
        return "/backend/product/create.html";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    String addProducts(@Valid @ModelAttribute ProductDto productsDto,
                       @RequestParam(value = "fileImage", required = false) MultipartFile mainImageMultipart,// ảnh chính
                       @RequestParam(value = "extraImage", required = false) MultipartFile[] extraImageMultiparts,// ảnh thêm
                       @RequestParam(name = "imageIDs", required = false) String[] imageIDs, //ID ảnh cũ còn giữ lại, cập nhật sản phẩm
                       @RequestParam(name = "imageNames", required = false) String[] imageNames,//Tên ảnh cũ còn giữ lại, cập nhật sản phẩm
                       @RequestParam(name = "productSizeIDs", required = false) String[] productSizeIDs,
                       @RequestParam(name = "solds", required = false) Integer[] solds,
                       @RequestParam(name = "inStocks", required = false) Integer[] inStocks,
                       @RequestParam(name = "sizeIDs", required = false) String[] sizeIDs,
                       @RequestParam(name = "newSolds", required = false) Integer[] newSolds,
                       @RequestParam(name = "newInStocks", required = false) Integer[] newInStocks,
                       @RequestParam(name = "newSizeIDs", required = false) String[] newSizeIDs,
                       BindingResult bindingResult,
                       Model model,
                       RedirectAttributes redirectAttributes) throws IOException {
        Object result = null;
        String msg = "";

        if (bindingResult.hasErrors()) return "/backend/product/create.html";
        Long id = productsDto.getId();

        ProductSaveFileHelper.setMainImageName(mainImageMultipart, productsDto);
        ProductSaveFileHelper.setExistingExtraImageNames(imageIDs, imageNames, productsDto);
        ProductSaveFileHelper.setNewExtraImageNames(extraImageMultiparts, productsDto);
        ProductSaveFileHelper.setExistingProductSizes(productSizeIDs, solds, inStocks, sizeIDs, productsDto);
        ProductSaveFileHelper.setNewProductSizes(newSolds, newInStocks, newSizeIDs, productsDto);

        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        if (productsDto.getId() == null) {
            productsDto.setCreatedTime(timestamp);
            productsDto.setUpdatedTime(timestamp);
            result = productsService.create(productsDto);
            id = ((ProductDto) result).getId();
            msg = " Tạo mới";
        } else {
            productsDto.setUpdatedTime(timestamp);
            ProductDto p = productsService.get(productsDto.getId());
            productsDto.setCreatedTime(p.getCreatedTime());
            result = productsService.create(productsDto);
            msg = "Cập nhật";
        }

        ProductSaveFileHelper.deleteExtraImagesWeredRemovedOnForm(productsDto);

        ProductSaveFileHelper.saveUploadedImages(mainImageMultipart, extraImageMultiparts, productsDto);


        if (Objects.equals(result, null)) {
            model.addAttribute("message", msg + " fail");
            return "/backend/product/create.html";
        }
        redirectAttributes.addFlashAttribute("message", msg + " product " + id + " thành công");
        return "redirect:/backend/product/" + id;

    }
}
