package com.canhchim.response;

import com.canhchim.models.dto.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartResponse {
    Collection<OrderItem> orderList;
    long total;
    int productCount;
    int itemCount;
}
