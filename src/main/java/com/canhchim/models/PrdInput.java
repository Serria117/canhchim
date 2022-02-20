package com.canhchim.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "PRD_Input")
@Getter
@Setter
public class PrdInput {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "total_cost", nullable = false, precision = 10)
    private BigDecimal totalCost;

    @Column(name = "input_date1", nullable = false)
    private Instant inputDate1;

    @Column(name = "input_date2", nullable = false)
    private Integer inputDate2;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "input_user_id", nullable = false)
    private ShpUser shpUser;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "supplier_id", nullable = false)
    private PrdSupplier prdSuppliers;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "PRD_input_detail",
            joinColumns = @JoinColumn(name = "input_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private Set<PrdProduct> listInputProducts = new HashSet<>();

}
