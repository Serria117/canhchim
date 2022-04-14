package com.canhchim.models.dto;

import com.canhchim.models.PrdOrderDetail;
import com.canhchim.models.PrdProduct;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {
    private long productId;
    private String productName;
    private long price;
    private int quantity;
    private long subTotal;
    private int discountPrice;

    public OrderItem(PrdProduct p, int quantity) {
        this.productId = p.getId();
        this.productName = p.getProductName();
        this.price = p.getProductPrice();
        this.quantity = quantity;
        this.subTotal = this.quantity * this.price;
    }

    public OrderItem(PrdOrderDetail od)
    {
        this.productId = od.getProduct().getId();
        this.productName = od.getProduct().getProductName();
        this.price = od.getProductSalePrice();
        this.quantity = od.getProductQuantity();
        this.subTotal = this.quantity * this.price;
    }
}
