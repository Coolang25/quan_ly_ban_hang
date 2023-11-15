package com.t3h.quan_ly_ban_hang.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderedItemDto {
    private Long id;
    private Integer quantity;
    private Long cartItemId;
    private Long productSizeId;
    private Long billId;

    private ProductDto productDto;
    private String sizeName;

    public OrderedItemDto(Integer quantity, Long cartItemId, Long productSizeId) {
        this.quantity = quantity;
        this.cartItemId = cartItemId;
        this.productSizeId = productSizeId;
    }
}
