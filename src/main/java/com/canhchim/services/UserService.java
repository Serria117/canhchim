package com.canhchim.services;

import com.canhchim.models.ShpRole;
import com.canhchim.models.ShpUser;
import com.canhchim.repositories.ShpUserRepository;
import com.canhchim.securityconfig.useraccount.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashSet;
import java.util.Set;

public class UserService implements UserDetailsService
{
   @Autowired
    ShpUserRepository userRepository;

    /**
     *
     * @param username
     * @return the userdetails
     * @throws UsernameNotFoundException
     */

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        ShpUser user = userRepository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username does not exist"));
        Set<GrantedAuthority> authorities = new HashSet<>();
        user.getShpRoles()
                .stream()
                .map(ShpRole::getRoleName)
                .forEach(roleName -> authorities.add(new SimpleGrantedAuthority(roleName)));
        return new CustomUserDetails(
                user.getUserName(),
                user.getPassword(),
                user.getShpShops(),
                authorities
        );
    }
}
