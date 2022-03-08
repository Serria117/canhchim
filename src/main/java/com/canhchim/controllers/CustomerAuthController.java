package com.canhchim.controllers;

import com.canhchim.models.CtmCustomer;
import com.canhchim.models.dto.CustomerLoginRequestDTO;
import com.canhchim.models.dto.CustomerRegisterDTO;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/customerauth")
public class CustomerAuthController
{
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    CustomerService customerService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("login")
    public ResponseEntity<?> customerLogin(@RequestBody CustomerLoginRequestDTO customerLoginRequestDTO) throws BadCredentialsException
    {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    customerLoginRequestDTO.getUsername(),
                    customerLoginRequestDTO.getPassword()
            ));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad credential.");
        }
        UserDetails userDetails = customerService.loadUserByUsername(customerLoginRequestDTO.getUsername());
        String jwt = jwtUtils.generateToken(userDetails);
        return ResponseEntity.ok().body(new ResponseObject(200, "OK", jwt));
    }

    @PostMapping("register")
    public ResponseEntity<?> customerRegister(@Valid @RequestBody CustomerRegisterDTO registerReq)
    {

        CtmCustomer newCustomer = new CtmCustomer();
        newCustomer.setCustomerName(registerReq.getUsername());
        newCustomer.setPhone(registerReq.getPhone());
        newCustomer.setPassword(passwordEncoder.encode(registerReq.getPassword()));

        try {
            var saveCustomer = customerService.register(newCustomer);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.ok().body("Customer saved");
    }

    @PostMapping("checkusername")
    public ResponseEntity<?> checkUsername(@RequestParam String username)
    {
        if (customerService.usernameExist(username)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username already exist.");
        }
        return ResponseEntity.ok("Username is valid.");
    }

    public ResponseEntity<?> checkPhone(@RequestParam String phone)
    {
        if (customerService.phoneExist(phone)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Phone number already exist.");
        }
        return ResponseEntity.status(HttpStatus.OK).body("Phone number is valid.");
    }
}
