package com.t3h.quan_ly_ban_hang.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class YourRequestObject {
    private List<Long> cartItemIDs;
    private List<Integer> quantities;
    private Integer totalPrice;
}
