package com.canhchim.models.dto;

import com.canhchim.models.dto.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDto
{
    Collection<OrderItem> orderList;
    long total;
    int productCount;
    int itemCount;
}
