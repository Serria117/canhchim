package com.canhchim.models.dto;

import com.canhchim.models.CtmCustomer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartDto
{
    Collection<OrderItem> items;
    long total;
    int productCount;
    int itemCount;


    public CartDto (List<OrderItem> items, long sumTotal, int productCount, int itemCount)
    {
        this.items = items;
        this.total = sumTotal;
        this.productCount = productCount;
        this.itemCount = itemCount;

    }

    public long calculateTotal ()
    {
        return items.stream()
                    .mapToLong(od -> od.getPrice() * od.getQuantity())
                    .sum();
    }

    public int countProduct ()
    {
        return items.size();
    }

    public int countItem ()
    {
        return items.stream()
                    .mapToInt(OrderItem::getQuantity)
                    .sum();
    }
}
