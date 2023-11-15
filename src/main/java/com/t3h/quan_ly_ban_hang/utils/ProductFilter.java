package com.t3h.quan_ly_ban_hang.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductFilter {
    private String priceLevel; //contain
    private String keyWord; // contain
    private String sortBy;
    private int page;

}
