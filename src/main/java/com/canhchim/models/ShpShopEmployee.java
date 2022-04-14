package com.canhchim.models;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "SHP_Shop_Employee")
public class ShpShopEmployee
{
    @EmbeddedId
    private ShpShopEmployeeId id = new ShpShopEmployeeId();

    @MapsId("shopId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "shopId", nullable = false)
    private ShpShop shop;

    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "userId", nullable = false)
    private ShpUser user;


    public ShpShopEmployeeId getId ()
    {
        return id;
    }

    public void setId (ShpShopEmployeeId id)
    {
        this.id = id;
    }

    public ShpShop getShop ()
    {
        return shop;
    }

    public void setShop (ShpShop shop)
    {
        this.shop = shop;
    }

    public ShpUser getUser ()
    {
        return user;
    }

    public void setUser (ShpUser user)
    {
        this.user = user;
    }
}
