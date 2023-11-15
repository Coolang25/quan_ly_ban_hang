package com.t3h.quan_ly_ban_hang.controller;

import com.t3h.quan_ly_ban_hang.dto.CartItemDto;
import com.t3h.quan_ly_ban_hang.dto.CommentDto;
import com.t3h.quan_ly_ban_hang.dto.ProductDto;
import com.t3h.quan_ly_ban_hang.dto.UserDto;
import com.t3h.quan_ly_ban_hang.entities.Products;
import com.t3h.quan_ly_ban_hang.service.*;
import com.t3h.quan_ly_ban_hang.utils.ProductFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class HomeController {
    @Autowired
    CategoryService categoryService;
    @Autowired
    BrandService brandService;
    @Autowired
    ProductService productService;
    @Autowired
    CartItemService cartItemService;
    @Autowired
    ProductImageService productImageService;
    @Autowired
    SizeService sizeService;

    @GetMapping(value = {"home", "/"})
    public String home(Model model, @RequestParam(required = false) String name) {
        model.addAttribute("categories", categoryService.findAll(""));
        model.addAttribute("brands", brandService.findAll(""));
        model.addAttribute("trendProducts", productService.findTrendProducts());
        model.addAttribute("newProducts", productService.findNewProducts());
        return "frontend/index.html";
    }

    @GetMapping(value = {"shop"})
    public String shop(Model model, @ModelAttribute("filter1") ProductFilter filter1) {
        model.addAttribute("categories", categoryService.findAll(""));
        model.addAttribute("brands", brandService.findAll(""));

        if (filter1.getPriceLevel() == null) {
           filter1.setPriceLevel("level");
        }
        if (filter1.getKeyWord() == null) {
            filter1.setKeyWord("");
        }

        if (filter1.getPage() == 0) {
            filter1.setPage(1);
        }

        if (filter1.getSortBy() == null || filter1.getSortBy().isEmpty()) {
            Page<ProductDto> productDtos = productService.findProductsByPriceLevelContainingAndNameContaining(filter1.getPriceLevel(), filter1.getKeyWord(), filter1.getPage());
            if (productDtos.isEmpty())
                model.addAttribute("notification", "Không tìm thấy sản phẩm");
            else {
                model.addAttribute("currentPage", filter1.getPage());
                model.addAttribute("products", productDtos);
                model.addAttribute("totalPage", (Integer)productDtos.getTotalPages());
            }
        } else {
            if (filter1.getSortBy().equalsIgnoreCase("latest")) {
                Page<ProductDto> productDtos = productService.findProductsByPriceLevelContainingAndNameContainingAndOrderByCreatedTimeDesc(filter1.getPriceLevel(), filter1.getKeyWord(), filter1.getPage());
                if (productDtos.isEmpty())
                    model.addAttribute("notification", "Không tìm thấy sản phẩm");
                else {
                    model.addAttribute("currentPage", filter1.getPage());
                    model.addAttribute("products", productDtos);
                    model.addAttribute("totalPage", (Integer)productDtos.getTotalPages());
                }
            }

            if (filter1.getSortBy().equalsIgnoreCase("asc")) {
                Page<ProductDto> productDtos = productService.findProductsByPriceLevelContainingAndNameContainingAndOrderByPriceAsc(filter1.getPriceLevel(), filter1.getKeyWord(), filter1.getPage());
                if (productDtos.isEmpty())
                    model.addAttribute("notification", "Không tìm thấy sản phẩm");
                else {
                    model.addAttribute("currentPage", filter1.getPage());
                    model.addAttribute("products", productDtos);
                    model.addAttribute("totalPage", (Integer)productDtos.getTotalPages());
                }
            }

            if (filter1.getSortBy().equalsIgnoreCase("desc")) {
                Page<ProductDto> productDtos = productService.findProductsByPriceLevelContainingAndNameContainingAndOrderByPriceDesc(filter1.getPriceLevel(), filter1.getKeyWord(), filter1.getPage());
                if (productDtos.isEmpty())
                    model.addAttribute("notification", "Không tìm thấy sản phẩm");
                else {
                    model.addAttribute("currentPage", filter1.getPage());
                    model.addAttribute("products", productDtos);
                    model.addAttribute("totalPage", (Integer)productDtos.getTotalPages());
                }
            }
        }

        return "frontend/shop.html";
    }

    @GetMapping(value = {"shop/category/{id}"})
    public String shopByCategory(Model model, @ModelAttribute("filter2") ProductFilter filter2, @PathVariable Long id) {
        model.addAttribute("categories", categoryService.findAll(""));
        model.addAttribute("brands", brandService.findAll(""));
        model.addAttribute("categoryId", id);
        if (filter2.getPriceLevel() == null) {
            filter2.setPriceLevel("level");
        }
        if (filter2.getKeyWord() == null) {
            filter2.setKeyWord("");
        }

        if (filter2.getPage() == 0) {
            filter2.setPage(1);
        }

        if (filter2.getSortBy() == null || filter2.getSortBy().isEmpty()) {
            Page<ProductDto> productDtos = productService.findProductsByCategoryIdAndPriceLevelContainingAndNameContaining(id, filter2.getPriceLevel(), filter2.getKeyWord(), filter2.getPage());
            if (productDtos.isEmpty())
                model.addAttribute("notification", "Không tìm thấy sản phẩm");
            else {
                model.addAttribute("currentPage", filter2.getPage());
                model.addAttribute("productsByCategory", productDtos);
                model.addAttribute("totalPage", (Integer)productDtos.getTotalPages());
            }
        } else {
            if (filter2.getSortBy().equalsIgnoreCase("latest")) {
                Page<ProductDto> productDtos = productService.findProductsByCategoryIdAndPriceLevelContainingAndNameContainingOrderByCreatedTimeDesc(id, filter2.getPriceLevel(), filter2.getKeyWord(), filter2.getPage());
                if (productDtos.isEmpty())
                    model.addAttribute("notification", "Không tìm thấy sản phẩm");
                else {
                    model.addAttribute("currentPage", filter2.getPage());
                    model.addAttribute("productsByCategory", productDtos);
                    model.addAttribute("totalPage", (Integer)productDtos.getTotalPages());
                }
            }

            if (filter2.getSortBy().equalsIgnoreCase("asc")) {
                Page<ProductDto> productDtos = productService.findProductsByCategoryIdAndPriceLevelContainingAndNameContainingOrderByPriceAsc(id, filter2.getPriceLevel(), filter2.getKeyWord(), filter2.getPage());
                if (productDtos.isEmpty())
                    model.addAttribute("notification", "Không tìm thấy sản phẩm");
                else {
                    model.addAttribute("currentPage", filter2.getPage());
                    model.addAttribute("productsByCategory", productDtos);
                    model.addAttribute("totalPage", (Integer)productDtos.getTotalPages());
                }
            }

            if (filter2.getSortBy().equalsIgnoreCase("desc")) {
                Page<ProductDto> productDtos = productService.findProductsByCategoryIdAndPriceLevelContainingAndNameContainingOrderByPriceDesc(id, filter2.getPriceLevel(), filter2.getKeyWord(), filter2.getPage());
                if (productDtos.isEmpty())
                    model.addAttribute("notification", "Không tìm thấy sản phẩm");
                else {
                    model.addAttribute("currentPage", filter2.getPage());
                    model.addAttribute("productsByCategory", productDtos);
                    model.addAttribute("totalPage", (Integer)productDtos.getTotalPages());
                }
            }
        }

        return "frontend/shop.html";
    }

    @GetMapping(value = {"shop/brand/{id}"})
    public String shopByBrand(Model model, @ModelAttribute("filter3") ProductFilter filter3, @PathVariable Long id) {
        model.addAttribute("categories", categoryService.findAll(""));
        model.addAttribute("brands", brandService.findAll(""));
        model.addAttribute("brandId", id);


        if (filter3.getPriceLevel() == null) {
            filter3.setPriceLevel("level");
        }
        if (filter3.getKeyWord() == null) {
            filter3.setKeyWord("");
        }

        if (filter3.getPage() == 0) {
            filter3.setPage(1);
        }

        if (filter3.getSortBy() == null || filter3.getSortBy().isEmpty()) {
            Page<ProductDto> productDtos = productService.findProductsByBrandIdAndPriceLevelContainingAndNameContaining(id, filter3.getPriceLevel(), filter3.getKeyWord(), filter3.getPage());
            if (productDtos.isEmpty())
                model.addAttribute("notification", "Không tìm thấy sản phẩm");
            else {
                model.addAttribute("currentPage", filter3.getPage());
                model.addAttribute("productsByBrand", productDtos);
                model.addAttribute("totalPage", (Integer)productDtos.getTotalPages());
            }
        } else {
            if (filter3.getSortBy().equalsIgnoreCase("latest")) {
                Page<ProductDto> productDtos = productService.findProductsByBrandIdAndPriceLevelContainingAndNameContainingOrderByCreatedTimeDesc(id, filter3.getPriceLevel(), filter3.getKeyWord(), filter3.getPage());
                if (productDtos.isEmpty())
                    model.addAttribute("notification", "Không tìm thấy sản phẩm");
                else {
                    model.addAttribute("currentPage", filter3.getPage());
                    model.addAttribute("productsByBrand", productDtos);
                    model.addAttribute("totalPage", (Integer)productDtos.getTotalPages());
                }
            }

            if (filter3.getSortBy().equalsIgnoreCase("asc")) {
                Page<ProductDto> productDtos = productService.findProductsByBrandIdAndPriceLevelContainingAndNameContainingOrderByPriceAsc(id, filter3.getPriceLevel(), filter3.getKeyWord(), filter3.getPage());
                if (productDtos.isEmpty())
                    model.addAttribute("notification", "Không tìm thấy sản phẩm");
                else {
                    model.addAttribute("currentPage", filter3.getPage());
                    model.addAttribute("productsByBrand", productDtos);
                    model.addAttribute("totalPage", (Integer)productDtos.getTotalPages());
                }
            }

            if (filter3.getSortBy().equalsIgnoreCase("desc")) {
                Page<ProductDto> productDtos = productService.findProductsByBrandIdAndPriceLevelContainingAndNameContainingOrderByPriceDesc(id, filter3.getPriceLevel(), filter3.getKeyWord(), filter3.getPage());
                if (productDtos.isEmpty())
                    model.addAttribute("notification", "Không tìm thấy sản phẩm");
                else {
                    model.addAttribute("currentPage", filter3.getPage());
                    model.addAttribute("productsByBrand", productDtos);
                    model.addAttribute("totalPage", (Integer)productDtos.getTotalPages());
                }
            }
        }

        return "frontend/shop.html";
    }

    @GetMapping(value = {"detail/{id}"})
    public String detailProduct(Model model, @PathVariable Long id) {
        CommentDto commentDto = new CommentDto();
        CartItemDto cartItemDto = new CartItemDto();
        cartItemDto.setIntendedQuantity(1);
        model.addAttribute("categories", categoryService.findAll(""));
        model.addAttribute("brands", brandService.findAll(""));
        model.addAttribute("product", productService.get(id));
        model.addAttribute("cartItemDto", cartItemDto);
        model.addAttribute("commentDto", commentDto);
        return "frontend/detail.html";
    }
}
