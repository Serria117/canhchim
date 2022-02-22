package com.canhchim.services;

import com.canhchim.models.dto.OrderItem;

import java.util.List;

public interface IOrderService {
    //add item into cart map:
    void addToCart(OrderItem item);

    //remove item from cart
    OrderItem remove(long id);

    List<OrderItem> getOrderList();

    void clearCart();

    OrderItem updateCart(long id, int quantity);

    long sumTotal();

    int productCount();
}
