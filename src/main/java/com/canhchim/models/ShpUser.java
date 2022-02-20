package com.canhchim.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "SHP_User")
@Setter @Getter
public class ShpUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "user_name", nullable = false, length = 20)
    private String userName;

    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName;

    @Column(name = "phone", nullable = false, length = 12)
    private String phone;

    @Column(name = "email", nullable = false, length = 60)
    private String email;

    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @Column(name = "citizen_identity", length = 100)
    private String citizenIdentity;

    @Column(name = "address", length = 100)
    private String address;

    @Column(name = "user_function_key", length = 12)
    private String userFunctionKey;

    @Column(name = "last_active_time", precision = 10)
    private BigDecimal lastActiveTime;

    @Column(name = "active_duration")
    private Integer activeDuration;

    @Column(name = "RSA_public", length = 100)
    private String rsaPublic;

    @Column(name = "RSA_private", length = 100)
    private String rsaPrivate;

    @Column(name = "last_active_ip", length = 100)
    private String lastActiveIp;

    @Column(name = "user_devices")
    private String userDevices;

    @ManyToMany
    @JoinTable(name = "SHP_Role_of_user",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<ShpRole> shpRoles = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<PrdOrder> prdOrders = new LinkedHashSet<>();

    @OneToMany(mappedBy = "shpUser")
    private Set<ShpShop> shpShops = new LinkedHashSet<>();

    //TODO Reverse Engineering! Migrate other columns to the entity
}
