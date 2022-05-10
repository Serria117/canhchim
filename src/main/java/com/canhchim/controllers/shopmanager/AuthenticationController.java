package com.canhchim.controllers.shopmanager;

import com.canhchim.payload.request.SetUserActionRequest;
import com.canhchim.payload.request.UserLoginRequest;
import com.canhchim.payload.request.UserRegisterRequest;
import com.canhchim.payload.response.ResponseObject;
import com.canhchim.securityconfig.JwtUtils;
import com.canhchim.securityconfig.customuserdetail.CustomUserDetails;
import com.canhchim.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.TreeMap;

@RestController
@RequestMapping(path = "/shop-auth")
public class AuthenticationController
{
    AuthenticationManager authenticationManager;
    JwtUtils jwtUtils;
    UserService userService;
    PasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager,
                                    JwtUtils jwtUtils,
                                    UserService userService,
                                    PasswordEncoder passwordEncoder)
    {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * <b>REGISTER NEW USER</b>
     * <br/>
     *
     * @param userRegister
     * @return
     */
    @PostMapping("register")

    public ResponseEntity<?> addNewUser(@RequestBody @Valid UserRegisterRequest userRegister)
    {
        try {
            userRegister.setPassword(passwordEncoder.encode(userRegister.getPassword()));
            var result = userService.register(userRegister);
            return ResponseEntity.ok().body(new ResponseObject(
                    200, "OK", result
            ));
        }
        catch ( Exception e ) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject(
                    400, "BAD REQUEST", e.getMessage()
            ));
        }
    }

    /**
     * <b>SHOP'S EMPLOYEE LOG IN</b>
     *
     * @param userLogin
     * @return
     * @throws BadCredentialsException
     */
    @PostMapping("login")
    public ResponseEntity<?> shopUserLogin(@RequestBody UserLoginRequest userLogin) throws BadCredentialsException
    {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    userLogin.getUsername(),
                    userLogin.getPassword()
            ));

            var userDetails = (CustomUserDetails) userService.loadUserByUsername(userLogin.getUsername());
            String token = jwtUtils.generateToken(userDetails);
            var user = userService.findByName(userLogin.getUsername());
            user.setLastActiveTime(System.currentTimeMillis());
            userService.save(user);

            Map<String, Object> data = new TreeMap<>();
            var loginTime = java.time.Instant.ofEpochMilli(user.getLastActiveTime());
            var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                                             .withZone(ZoneId.systemDefault());
            data.put("loginTime", formatter.format(loginTime));
            data.put("token", token);
            data.put("shops", userDetails.getShopIds());
            return ResponseEntity.ok().body(new ResponseObject(
                    200,
                    "OK",
                    data));
        }
        catch ( Exception e ) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body(new ResponseObject(
                                         400,
                                         "BAD REQUEST",
                                         e.getMessage()
                                 ));
        }
    }

    /**
     * <b>UPDATE AUTHORITY (ACTION) FOR USER</b>
     *
     * @param request
     * @return
     */
    @PatchMapping("set-authority")
    public ResponseEntity<?> setUserActions(@RequestBody SetUserActionRequest request)
    {
        //Check for valid user in the database:
        var foundUser = userService.findById(request.getUserId());
        if ( foundUser == null ) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid User's Id");
        }
        userService.setRoles(foundUser, request.getActionId());
        return ResponseEntity.ok("User updated.");
    }
}
