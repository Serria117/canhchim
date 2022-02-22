package com.canhchim.models.dto;

import com.canhchim.models.PrdProduct;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;

@Data
@AllArgsConstructor @NoArgsConstructor
public class OrderItem {
    private long productId;
    private String productName;
    private long price;
    @Min(value = 0, message="Quantity can not be negative number.")
    private int quantity;

    public OrderItem(PrdProduct p, int quantity) {
        this.productId = p.getId();
        this.productName = p.getProductName();
        this.price = p.getProductPrice();
        this.quantity = quantity;
    }
}
