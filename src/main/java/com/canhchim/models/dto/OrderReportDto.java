package com.canhchim.models.dto;

import com.canhchim.models.PrdOrderDetail;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class OrderReportDto implements Serializable
{
    long orderId;
    String orderCode;
    long total;
    long productCount;
    long itemCount;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    LocalDateTime orderTime;
    long userId;
    long table;
    /*int orderType;
    int paymentType;*/
    List<OrderItem> orderItems = new ArrayList<>();

    public void addItem(PrdOrderDetail item)
    {
        var orderItem = new OrderItem(item);
        orderItems.add(orderItem);
        this.orderId = item.getOrder().getId();
        this.orderCode = item.getOrder().getOrderCode();
        this.userId = item.getOrder().getUser().getId();
        this.table = item.getOrder().getTable();
        this.productCount = orderItems.size();
        this.itemCount = orderItems.stream().mapToLong(OrderItem::getQuantity).sum();
    }
}
