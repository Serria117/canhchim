package com.canhchim.controllers.shopmanager;

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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/shop-auth")
public class LoginController
{
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    UserService userService;
    @Autowired
    PasswordEncoder passwordEncoder;

    /**
     * REGISTER NEW USER
     * <p>
     * * @param userRegister
     *
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
     * SHOP'S EMPLOYEE LOG IN
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
            data.put("allowedActions", userDetails.getAuthorities()
                                                  .stream()
                                                  .map(GrantedAuthority::getAuthority)
                                                  .collect(Collectors.toList()));

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
}
