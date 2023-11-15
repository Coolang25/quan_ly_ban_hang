package com.t3h.quan_ly_ban_hang.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductSizeDto {
    private Long id;
    private Integer sold;
    private Integer inStock;
    private Long productId;
    private Long sizeId;

    private String sizeName;

    public ProductSizeDto (Integer sold, Integer inStock, Long sizeId) {
        this.sold = sold;
        this.inStock = inStock;
        this.sizeId = sizeId;
    }

    public ProductSizeDto (Long id, Integer sold, Integer inStock, Long sizeId) {
        this.id = id;
        this.sold = sold;
        this.inStock = inStock;
        this.sizeId = sizeId;
    }

    public ProductSizeDto(Long id, Integer sold, Integer inStock, Long productId, Long sizeId) {
        this.id = id;
        this.sold = sold;
        this.inStock = inStock;
        this.productId = productId;
        this.sizeId = sizeId;
    }
}
