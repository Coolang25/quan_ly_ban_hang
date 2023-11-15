package com.t3h.quan_ly_ban_hang.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDto {
    private Long id;
    private Integer intendedQuantity;
    private Long productSizeId;
    private Long userId;
    private Boolean status;

    private ProductDto productDto;
    private String sizeName;
    private Integer inStock;

}
