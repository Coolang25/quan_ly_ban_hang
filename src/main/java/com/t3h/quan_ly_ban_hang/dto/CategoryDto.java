package com.t3h.quan_ly_ban_hang.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
    private Long id;
    private String image;
    private String name;
    private Boolean status;

    MultipartFile fileImage;

    public CategoryDto(Long id, String image, String name) {
        this.id = id;
        this.image = image;
        this.name = name;
    }

    public String getImagePath() {
        if (image == null)
            return "/images/image-thumbnail.png";

        return "/image-storage/category/" + this.id + "/" + this.image;
    }
}
