package com.canhchim.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "PRD_Suppliers")
@Getter @Setter
public class PrdSupplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "supplier_name", nullable = false, length = 100)
    private String supplierName;

    @Column(name = "supplier_code", length = 20)
    private String supplierCode;

    @Column(name = "supplier_address", nullable = false)
    private String supplierAddress;

    @OneToMany(mappedBy = "prdSuppliers")
    private Set<PrdInput> prdInputs = new LinkedHashSet<>();

    //TODO Reverse Engineering! Migrate other columns to the entity
}
