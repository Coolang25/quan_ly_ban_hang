package com.t3h.quan_ly_ban_hang.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductImageDto {
    private Long id;
    private String name;
    private Long productId;

    MultipartFile fileImage;

    public ProductImageDto (String name) {
        this.name = name;
    }

    public ProductImageDto(String name, Long productId) {
        this.name = name;
        this.productId = productId;
    }

    public ProductImageDto(Long id, String name, Long productId) {
        this.id = id;
        this.name = name;
        this.productId = productId;
    }

    public String getImagePath() {
        if (id == null || StringUtils.isEmpty(name)) return "/images/image-thumbnail.png";

        return "/image-storage/product/" + this.productId + "/extras/" + this.name;
    }
}
