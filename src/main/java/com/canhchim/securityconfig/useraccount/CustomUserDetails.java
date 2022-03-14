package com.canhchim.securityconfig.useraccount;

import com.canhchim.models.ShpShop;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

public class CustomUserDetails implements UserDetails
{
    private final String username;
    private final String password;
    private final Set<ShpShop> shops;
    Set<GrantedAuthority> authorities;

    public CustomUserDetails(String username, String password, Set<ShpShop> shops, Set<GrantedAuthority> authorities)
    {
        this.username = username;
        this.password = password;
        this.shops = shops;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        return this.authorities;
    }

    @Override
    public String getPassword()
    {
        return this.password;
    }

    public Set<ShpShop> getShops()
    {
        return shops;
    }

    @Override
    public String getUsername()
    {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired()
    {
        return true;
    }

    @Override
    public boolean isAccountNonLocked()
    {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired()
    {
        return true;
    }

    @Override
    public boolean isEnabled()
    {
        return true;
    }
}
