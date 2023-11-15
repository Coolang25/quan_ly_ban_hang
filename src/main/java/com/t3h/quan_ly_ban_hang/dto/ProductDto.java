package com.t3h.quan_ly_ban_hang.dto;

import com.t3h.quan_ly_ban_hang.entities.ProductImages;
import com.t3h.quan_ly_ban_hang.entities.ProductSize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private Long id;
    private String name;
    private Integer cost;
    private Integer price;
    private String mainImage;
    private String shortDescription;
    private String fullDescription;
    private Timestamp createdTime;
    private Timestamp updatedTime;
    private Float discountPercent;
    private Long brandId;
    private Long categoryId;
    private String priceLevel;
    private Boolean status;

    private Integer sold;

    MultipartFile fileImage;
    List<MultipartFile> multipartFileList;
    List<ProductSizeDto> sizeList = new ArrayList<>();;
    List<ProductImageDto> images = new ArrayList<>();
    List<CommentDto> comments = new ArrayList<>();

    public String getMainImagePath() {
        if (id == null || mainImage == null) return "/images/image-thumbnail.png";

        return "/image-storage/product/" + this.id + "/" + this.mainImage;
    }

    public boolean containsImageName(String imageName) {
        Iterator<ProductImageDto> iterator = images.iterator();

        while (iterator.hasNext()) {
            ProductImageDto image = iterator.next();
            if (image.getName().equals(imageName)) {
                return true;
            }
        }

        return false;
    }

    public boolean containsSizeId(Long id) {
        Iterator<ProductSizeDto> iterator = sizeList.iterator();

        while (iterator.hasNext()) {
            ProductSizeDto productSize = iterator.next();
            if (productSize.getSizeId() == id) {
                return true;
            }
        }

        return false;
    }

    public void addExtraImage(String imageName) {
        this.images.add(new ProductImageDto (imageName));
    }
    public void addExtraSize(Integer sold, Integer inStock, Long sizeId) {
        this.sizeList.add(new ProductSizeDto(sold, inStock, sizeId));
    }
}
