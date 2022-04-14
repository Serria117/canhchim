package com.canhchim.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "PRD_Order")
@Getter
@Setter
public class PrdOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER) @JsonIgnore
    @JoinColumn(name = "shop_id")
    private ShpShop shop;

    @ManyToOne(fetch = FetchType.EAGER) @JsonIgnore
    @JoinColumn(name = "user_id")
    private ShpUser user;

    @Column(name = "order_code", nullable = false, length = 50)
    private String orderCode;

//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "customer_id", nullable = false)
//    private CtmCustomer ctmCustomer;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "shipper_id")
    private Long shipperId;

    @Column(name = "ship_company_id")
    private Long shipCompanyId;

    @Column(name = "order_date1", nullable = false)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDateTime orderDate1;

    @Column(name = "order_date2")
    private Long orderDate2;

    @Column(name = "total_price", nullable = false)
    private Long totalPrice;

    @Column(name = "currency_type")
    private Integer currencyType;

    @Column(name = "buyer_address", length = 100)
    private String buyerAddress;

    @Column(name = "delivery_address", length = 100)
    private String deliveryAddress;

    @Column(name = "payment_status")
    private Integer paymentStatus;

    @Column(name = "shipment_status")
    private Integer shipmentStatus;

    @Column(name = "shipment_note", length = 100)
    private String shipmentNote;

    @Column(name = "customer_phone", length = 12)
    private String customerPhone;

    @Column(name = "customer_name", length = 60)
    private String customerName;

    @OneToMany(mappedBy = "order")
    private Set<PrdOrderDetail> orderList = new HashSet<>();

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "table_id", nullable = false)
    private Long table;

    @Column(name = "cash_receive")
    private Integer cashReceive;

    @Column(name = "cash_change")
    private Integer cashChange;

    @Column(name = "order_type", nullable = false)
    private Integer orderType;

    @Column(name = "payment_type", nullable = false)
    private Integer paymentType;


//TODO Reverse Engineering! Migrate other columns to the entity
}
