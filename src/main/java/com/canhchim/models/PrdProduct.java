package com.canhchim.models;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.Instant;
import java.util.*;

@Entity
@Table(name = "PRD_Products", indexes = {
        @Index(name = "product_code", columnList = "product_code", unique = true)
})
@Getter
@Setter
public class PrdProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "product_code", nullable = false, length = 50)
    private String productCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_type")
    private PrdProductType prdProductType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_category")
    private PrdCategory prdCategories;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_price")
    private Long productPrice;

    @Column(name = "product_quantity")
    private Integer productQuantity;

    @Column(name = "supplier_id")
    private Long supplierId;

    @Column(name = "input_date")
    private Instant inputDate;

    @Column(name = "last_update")
    private Instant lastUpdate;

    @Column(name = "input_user")
    private Long inputUser;

    @Column(name = "last_update_user")
    private Long lastUpdateUser;

    @Column(name = "product_status")
    private Integer productStatus;

    @Column(name = "parent_product")
    private Long parentProduct;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "shop_id", nullable = false)
    private ShpShop shpShop;

    @OneToMany(mappedBy = "product")
    private Set<PrdImage> prdImages = new LinkedHashSet<>();

    @ManyToMany(mappedBy = "listInputProducts")
    private  List<PrdInput> listInput = new ArrayList<>();


    @Override
    public boolean equals(Object o)
    {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        PrdProduct that = (PrdProduct) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
