package com.canhchim.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "PRD_Order_detail")
@Getter
@Setter
public class PrdOrderDetail {
    @EmbeddedId
    private PrdOrderDetailId id = new PrdOrderDetailId();

    @MapsId("orderId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", nullable = false) @JsonIgnore
    private PrdOrder order;

    @MapsId("productId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private PrdProduct product;

    @Column(name = "product_sale_price", nullable = false)
    private Long productSalePrice;

    @Column(name = "product_quantity", nullable = false)
    private Integer productQuantity;

    @Column(name = "discount_type")
    private Integer discountType;

    @Column(name = "discount_amount")
    private Integer discountAmount;

    @Column(name = "discount_date_begin")
    private Double discountDateBegin;

    @Column(name = "discount_date_end")
    private Double discountDateEnd;

    @Column(name = "discount_code", length = 120)
    private String discountCode;


}
