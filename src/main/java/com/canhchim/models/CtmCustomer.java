package com.canhchim.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "CTM_Customer")
@Getter
@Setter
public class CtmCustomer {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "customer_name", nullable = false, length = 60)
    private String customerName;

    @Column(name = "phone", nullable = false, length = 12)
    private String phone;

    @Column(name = "main_address")
    private String mainAddress;

    @Column(name = "delivery_address")
    private String deliveryAddress;

    @OneToMany(mappedBy = "ctmCustomer")
    private Set<PrdOrder> prdOrders = new LinkedHashSet<>();

    //TODO Reverse Engineering! Migrate other columns to the entity
}