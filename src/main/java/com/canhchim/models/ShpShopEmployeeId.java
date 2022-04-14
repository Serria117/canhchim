package com.canhchim.models;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class ShpShopEmployeeId implements Serializable
{
    @Serial
    private static final long serialVersionUID = - 6618904973030103948L;
    @Column(name = "shopId", nullable = false)
    private Long shopId;
    @Column(name = "userId", nullable = false)
    private Long userId;

    public Long getShopId ()
    {
        return shopId;
    }

    public void setShopId (Long shopId)
    {
        this.shopId = shopId;
    }

    public Long getUserId ()
    {
        return userId;
    }

    public void setUserId (Long userId)
    {
        this.userId = userId;
    }

    @Override
    public boolean equals (Object o)
    {
        if ( this == o ) {
            return true;
        }
        if ( o == null || getClass() != o.getClass() ) {
            return false;
        }
        ShpShopEmployeeId that = (ShpShopEmployeeId) o;
        return shopId.equals(that.shopId) && userId.equals(that.userId);
    }

    @Override
    public int hashCode ()
    {
        return Objects.hash(shopId, userId);
    }
}
