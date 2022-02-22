package com.canhchim.services;

import com.canhchim.models.dto.OrderItem;
import com.canhchim.repositories.PrdOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@SessionScope
public class OrderService implements IOrderService {

    @Autowired
    PrdOrderRepository orderRepository;

    private final Map<Long, OrderItem> cart = new HashMap<>();

    //add item into cart map:
    @Override
    public void addToCart(OrderItem item) {
        OrderItem existItem = cart.get(item.getProductId());
        //if product has already existed in the cart, update its quantity:
        if (existItem != null) {
            existItem.setQuantity(item.getQuantity() + existItem.getQuantity());
        } else {
            cart.put(item.getProductId(), item);
        }
    }

    public OrderItem remove(long id) {
        return cart.remove(id);
    }

    @Override
    public List<OrderItem> getOrderList() {
        return cart.values().stream().toList();
    }

    //Clear all item in cart
    @Override
    public void clearCart() {
        cart.clear();
    }

    //Update quantity:
    @Override
    public OrderItem updateCart(long id, int quantity) {
        OrderItem item = cart.get(id);
        if (item != null) {
            item.setQuantity(item.getQuantity() + quantity);
            if (item.getQuantity() == 0) {
                cart.remove(id);
            }
        }
        return item;
    }

    //Calculate total:
    @Override
    public long sumTotal() {
        return cart.values().stream().mapToLong(i -> i.getQuantity() * i.getPrice()).sum();
    }

    //Count:
    @Override
    public int productCount() {
        return cart.values().size();
    }

    public int itemCount(){
        return cart.values().stream().mapToInt(OrderItem::getQuantity).sum();
    }
}
