package com.canhchim.models.dto;

import com.canhchim.models.PrdProduct;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
public class OrderItem {
    private long productId;
    private String productName;
    private long price;
    private int quantity;

    public OrderItem(PrdProduct p, int quantity) {
        this.productId = p.getId();
        this.productName = p.getProductName();
        this.price = p.getProductPrice();
        this.quantity = quantity;
    }
}
