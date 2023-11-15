package com.t3h.quan_ly_ban_hang.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BrandDto {
    private Long id;
    private String logo;
    private String name;
    private Boolean status;

    MultipartFile fileImage;

    public BrandDto(Long id, String logo, String name) {
        this.id = id;
        this.logo = logo;
        this.name = name;
    }

    public String getImagePath() {
        if (logo == null)
            return "/images/image-thumbnail.png";

        return "/image-storage/brand/" + this.id + "/" + this.logo;
    }
}
