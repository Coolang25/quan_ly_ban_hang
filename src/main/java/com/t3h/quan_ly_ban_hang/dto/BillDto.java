package com.t3h.quan_ly_ban_hang.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillDto {
    private Long id;
    private Timestamp createdTime;
    private int status;
    private Long userId;
    private Integer totalPrice;

    private List<OrderedItemDto> orderedItemDtos;

}
