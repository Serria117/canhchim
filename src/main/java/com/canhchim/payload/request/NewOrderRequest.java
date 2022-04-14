package com.canhchim.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewOrderRequest
{
    @Valid List<item> items;
    long total;
    long productCount;
    long itemCount;
    LocalDateTime orderTime;

    long userId;

    long shopId;

    long tableId;

    int orderType;

    int paymentType;


    public void countProduct()
    {
        this.productCount = items.stream().filter(Objects::nonNull).count();
    }

    public void countItem()
    {
        this.itemCount = items.stream().filter(Objects::nonNull).mapToLong(item::getQuantity).sum();
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class item
    {
        long id;
        String productName;
        @Positive(message = "Số lượng phải lớn hơn 0") int quantity;
        @Positive(message = "Giá tiền phải lớn hơn 0") long price;
        long subtotal;

    }
}
