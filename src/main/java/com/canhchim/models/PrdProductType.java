package com.canhchim.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "PRD_Product_type")
@Getter @Setter
public class PrdProductType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "type_name", nullable = false, length = 60)
    private String typeName;

    @Column(name = "type_code", nullable = false)
    private Long typeCode;

    //TODO Reverse Engineering! Migrate other columns to the entity
}
