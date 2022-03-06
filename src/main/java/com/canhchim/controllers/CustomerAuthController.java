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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

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
        Optional<CtmCustomer> findName = customerService.findCustomerByUsername(registerReq.getUsername());
        Optional<CtmCustomer> findPhone = customerService.findCustomerPhone(registerReq.getPhone());
        String duplicateUsername = "";
        String duplicatePhone = "";
        if (findName.isPresent()) {
            duplicateUsername = "Username has already been taken";
        }
        if (findPhone.isPresent()) {
            duplicatePhone = "Phone has already been taken";
        }
        if (!duplicateUsername.equals("") || !duplicatePhone.equals("")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new String[]{duplicateUsername, duplicatePhone});
        }

        CtmCustomer newCustomer = new CtmCustomer();
        newCustomer.setCustomerName(registerReq.getUsername());
        newCustomer.setPhone(registerReq.getPhone());
        newCustomer.setPassword(passwordEncoder.encode(registerReq.getPassword()));

        return ResponseEntity.status(HttpStatus.OK).body(customerService.save(newCustomer));
    }
}
