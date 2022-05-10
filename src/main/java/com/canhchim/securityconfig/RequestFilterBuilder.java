package com.canhchim.securityconfig;

import com.canhchim.services.CustomerService;
import com.canhchim.services.UserService;

public class RequestFilterBuilder {
    private CustomerService customerService;
    private UserService userService;
    private JwtUtils jwtUtils;

    public RequestFilterBuilder setCustomerService(CustomerService customerService)
    {
        this.customerService = customerService;
        return this;
    }

    public RequestFilterBuilder setUserService(UserService userService)
    {
        this.userService = userService;
        return this;
    }

    public RequestFilterBuilder setJwtUtils(JwtUtils jwtUtils)
    {
        this.jwtUtils = jwtUtils;
        return this;
    }

    public RequestFilter createRequestFilter()
    {
        return new RequestFilter(customerService, userService, jwtUtils);
    }
}
