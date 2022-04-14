package com.canhchim.services;

import com.canhchim.models.PrdOrder;
import com.canhchim.models.PrdOrderDetail;
import com.canhchim.models.dto.OrderItem;
import com.canhchim.repositories.CtmCustomerRepository;
import com.canhchim.repositories.PrdOrderDetailRepository;
import com.canhchim.repositories.PrdOrderRepository;
import com.canhchim.repositories.PrdProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.time.LocalDateTime;
import java.util.*;

@Service
@SessionScope
public class OrderService implements IOrderService
{

    private final Map<Long, OrderItem> cart = new HashMap<>();
    @Autowired
    PrdOrderRepository orderRepository;
    @Autowired
    PrdProductRepository productRepository;
    @Autowired
    CtmCustomerRepository customerRepository;
    @Autowired
    PrdOrderDetailRepository orderDetailRepository;

    //add item into cart map:
    @Override
    public void addToCart(OrderItem item)
    {
        OrderItem existItem = cart.get(item.getProductId());
        //if product has already existed in the cart, update its quantity:
        if (existItem != null) {
            existItem.setQuantity(item.getQuantity() + existItem.getQuantity());
        } else {
            cart.put(item.getProductId(), item);
        }
    }

    public OrderItem remove(long id)
    {
        return cart.remove(id);
    }

    @Override
    public List<OrderItem> getOrderList()
    {
        return cart.values().stream().toList();
    }

    //Clear all item in cart
    @Override
    public void clearCart()
    {
        cart.clear();
    }

    //Update quantity:
    @Override
    public OrderItem updateCart(long id, int quantity)
    {
        OrderItem item = cart.get(id);
        if (item != null) {
            item.setQuantity(quantity);
            if (item.getQuantity() == 0) {
                cart.remove(id);
            }
        }
        return item;
    }

    //Calculate total:
    @Override
    public long sumTotal()
    {
        return cart.values().stream().mapToLong(i -> i.getQuantity() * i.getPrice()).sum();
    }

    //Count:
    @Override
    public int productCount()
    {
        return cart.values().size();
    }

    //Check out: create each order detail and add to the order
    public PrdOrder checkOut(long customerId) throws Exception
    {
        if (cart.isEmpty()) return null;

        PrdOrder createdOrder = new PrdOrder();

        createdOrder.setOrderDate1(LocalDateTime.now());
        Set<PrdOrderDetail> orderList = new HashSet<>();

        cart.forEach((id, item) -> {
            PrdOrderDetail oDetail = new PrdOrderDetail();
            oDetail.setProduct(productRepository.findById(id).orElse(null));
            oDetail.setProductQuantity(item.getQuantity());
            oDetail.setProductSalePrice(item.getPrice());

            orderList.add(oDetail);
        });
        createdOrder.setOrderList(orderList);
        return createdOrder;
    }

    public void saveOrder(PrdOrder order, Set<PrdOrderDetail> orderList)
    {
        var o = orderRepository.save(order);
        orderList.forEach(od -> od.setOrder(o));
        orderDetailRepository.saveAll(orderList);
    }

    public int itemCount()
    {
        return cart.values().stream().mapToInt(OrderItem::getQuantity).sum();
    }
}
