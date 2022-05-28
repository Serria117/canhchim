package com.canhchim.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "CTM_Customer")
@Getter
@Setter
public class CtmCustomer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "customer_name", nullable = false, length = 60)
    private String customerName;

    @Column(name = "password")
    @JsonIgnore
    private String password;

    @Column(name = "phone", nullable = false, length = 12)
    private String phone;

    @Column(name = "main_address")
    private String mainAddress;

    @Column(name = "delivery_address")
    private String deliveryAddress;

    //@OneToMany(mappedBy = "ctmCustomer") @JsonIgnore
    //private Set<PrdOrder> prdOrders = new LinkedHashSet<>();
    //TODO Reverse Engineering! Migrate other columns to the entity
    public CtmCustomer() {
    }
}
