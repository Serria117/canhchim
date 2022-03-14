package com.canhchim.services;

import com.canhchim.customexception.UsernameAlreadyExistException;
import com.canhchim.models.CtmCustomer;
import com.canhchim.repositories.CtmCustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService implements UserDetailsService
{
    @Autowired
    CtmCustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        CtmCustomer customer = customerRepository.findByCustomerName(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found."));
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add((new SimpleGrantedAuthority("ROLE_CUSTOMER")));
        return new User(
                customer.getCustomerName(),
                customer.getPassword(),
                authorities
        );
    }

    public Optional<CtmCustomer> findCustomerByUsername(String username)
    {
        return customerRepository.findByCustomerName(username);
    }

    public Optional<CtmCustomer> findCustomerPhone(String phone)
    {
        return customerRepository.findByPhone(phone);
    }

    public CtmCustomer register(CtmCustomer customer) throws UsernameAlreadyExistException
    {
        if (usernameExist(customer.getCustomerName())) {
            throw new UsernameAlreadyExistException("Username has already been taken.");
        }
        if (phoneExist(customer.getPhone())) {
            throw new UsernameAlreadyExistException("Phone number has already been registered.");
        }

        return customerRepository.save(customer);
    }

    public boolean usernameExist(String username)
    {
        return customerRepository.findByCustomerName(username).isPresent();
    }

    public boolean phoneExist(String phone)
    {
        return customerRepository.findByPhone(phone).isPresent();
    }
}
