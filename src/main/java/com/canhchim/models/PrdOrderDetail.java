package com.canhchim.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "PRD_Order_detail")
@Getter
@Setter
public class PrdOrderDetail {
    @EmbeddedId
    private PrdOrderDetailId id;

    @MapsId("orderId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    private PrdOrder order;

    @MapsId("productId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private PrdProduct product;

    @Column(name = "product_sale_price", nullable = false)
    private Integer productSalePrice;

    @Column(name = "product_quantity", nullable = false)
    private Integer productQuantity;

    @Column(name = "discount_type", nullable = false)
    private Integer discountType;

    @Column(name = "discount_amount", nullable = false)
    private Integer discountAmount;

    @Column(name = "discount_date_begin", nullable = false)
    private Double discountDateBegin;

    @Column(name = "discount_date_end", nullable = false)
    private Double discountDateEnd;

    @Column(name = "discount_code", nullable = false, length = 120)
    private String discountCode;


}
