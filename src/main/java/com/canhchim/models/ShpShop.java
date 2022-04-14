package com.canhchim.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "SHP_Shop")
@Getter
@Setter
public class ShpShop
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "shop_name", nullable = false, length = 100)
    private String shopName;

    @Column(name = "full_address")
    private String fullAddress;

    @Column(name = "add_street")
    private Integer addStreet;

    @Column(name = "add_ward")
    private Integer addWard;

    @Column(name = "add_district")
    private Integer addDistrict;

    @Column(name = "add_province")
    private Integer addProvince;

    @Column(name = "add_GPS_longitude")
    private Double addGpsLongitude;

    @Column(name = "add_GPS_latitude")
    private Double addGpsLatitude;

    @Column(name = "create_date")
    private Instant createDate;

    @Column(name = "update_date")
    private Instant updateDate;

    @Column(name = "RSA_private", length = 100)
    private String rsaPrivate;

    @Column(name = "RSA_public", length = 100)
    private String rsaPublic;

    @Column(name = "key_salt", length = 100)
    private String keySalt;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "approved_by_admin")
//    private SysAdmin sysAdmin;

    @OneToMany(mappedBy = "shop")
    private Set<ShpShopEmployee> employee = new LinkedHashSet<>();

//    @OneToMany(mappedBy = "shop")
//    private Set<PrdOrder> prdOrders = new LinkedHashSet<>();

    @OneToMany(mappedBy = "shpShop")
    private Set<PrdProduct> prdProducts = new LinkedHashSet<>();

    @OneToMany(mappedBy = "shop")
    private Set<PrdCategory> prdCategories = new LinkedHashSet<>();

    @OneToMany(mappedBy = "shop")
    private Set<ShpZone> shpZones = new LinkedHashSet<>();

    //TODO Reverse Engineering! Migrate other columns to the entity
}
