package com.canhchim.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "PRD_Order")
@Getter
@Setter
public class PrdOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id")
    private ShpShop shop;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private ShpUser user;

    @Column(name = "order_code", nullable = false, length = 50)
    private String orderCode;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private CtmCustomer ctmCustomer;

    @Column(name = "shipper_id", nullable = false)
    private Long shipperId;

    @Column(name = "ship_company_id", nullable = false)
    private Long shipCompanyId;

    @Column(name = "order_date1", nullable = false)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDateTime orderDate1;

    @Column(name = "order_date2", nullable = false)
    private Long orderDate2;

    @Column(name = "total_price", nullable = false, precision = 10)
    private BigDecimal totalPrice;

    @Column(name = "currency_type", nullable = false)
    private Integer currencyType;

    @Column(name = "buyer_address", nullable = false, length = 100)
    private String buyerAddress;

    @Column(name = "delivery_address", nullable = false, length = 100)
    private String deliveryAddress;

    @Column(name = "payment_status", nullable = false)
    private Integer paymentStatus;

    @Column(name = "shipment_status", nullable = false)
    private Integer shipmentStatus;

    @Column(name = "shipment_note", nullable = false, length = 100)
    private String shipmentNote;

    @Column(name = "customer_phone", nullable = false, length = 60)
    private String customerPhone;

    @Column(name = "customer_name", nullable = false, length = 12)
    private String customerName;

    @OneToMany(mappedBy = "order")
    private Set<PrdOrderDetail> orderList = new HashSet<>();


//TODO Reverse Engineering! Migrate other columns to the entity
}
