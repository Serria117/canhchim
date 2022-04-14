package com.canhchim.payload.request;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterRequest
{
    @NotNull
    private String fullName;
    @NotNull
    @Digits(integer = 12, fraction = 0)
    private String citizenIdentity;
    @NotNull
    private String username;
    @NotNull
    @Size(min = 6, max = 18)
    private String password;
    private String password2;
    @NotNull
    @Digits(integer = 12, fraction = 0)
    private String phoneNumber;
    @NotNull
    private long shopId;
    @NotNull
    private Long[] roleId;
    @Min(0)
    @Max(1)
    private int isOwner;

    public int getIsOwner ()
    {
        return isOwner;
    }

    public void setIsOwner (int isOwner)
    {
        this.isOwner = isOwner;
    }

    public String getUsername ()
    {
        return username;
    }

    public void setUsername (String username)
    {
        this.username = username;
    }

    public String getFullName ()
    {
        return fullName;
    }

    public void setFullName (String fullName)
    {
        this.fullName = fullName;
    }

    public String getCitizenIdentity ()
    {
        return citizenIdentity;
    }

    public void setCitizenIdentity (String citizenIdentity)
    {
        this.citizenIdentity = citizenIdentity;
    }

    public String getPassword ()
    {
        return password;
    }

    public void setPassword (String password)
    {
        this.password = password;
    }

    public String getPassword2 ()
    {
        return password2;
    }

    public void setPassword2 (String password2)
    {
        if ( !password2.equals(password) ) {
            throw new RuntimeException("Password does not match.");
        }
        this.password2 = password2;
    }

    public String getPhoneNumber ()
    {
        return phoneNumber;
    }

    public void setPhoneNumber (String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }

    public long getShopId ()
    {
        return shopId;
    }

    public void setShopId (long shopId)
    {
        this.shopId = shopId;
    }

    public Long[] getRoleId ()
    {
        return roleId;
    }

    public void setRoleId (Long[] roleId)
    {
        this.roleId = roleId;
    }
}
