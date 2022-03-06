package com.canhchim.controllers;

import com.canhchim.models.dto.LoginRequest;
import com.canhchim.payload.ResponseObject;
import com.canhchim.securityconfig.JwtUtils;
import com.canhchim.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/customerauth")
public class CustomerLoginController
{
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    CustomerService customerService;

    @PostMapping("login")
    public ResponseEntity<?> customerLogin(@RequestBody LoginRequest loginRequest) throws Exception
    {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsername(), loginRequest.getPassword()
            ));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad credential.");
        }
        UserDetails userDetails = customerService.loadUserByUsername(loginRequest.getUsername());
        String jwt = jwtUtils.generateToken(userDetails);
        return ResponseEntity.ok().body(new ResponseObject(200, "OK", jwt));
    }
}
