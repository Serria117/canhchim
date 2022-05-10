package com.canhchim.controllers.test;

import com.canhchim.securityconfig.customuserdetail.CustomUserDetails;
import com.canhchim.services.CustomerService;
import com.canhchim.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.TreeMap;

@RestController
@RequestMapping(path = "/test")
@AllArgsConstructor
public class TestController
{
    CustomerService customerService;
    UserService userService;

    @GetMapping("user-detail")
    public ResponseEntity<?> testUserDetail(Authentication auth)
    {
        if ( auth == null || !auth.isAuthenticated() ) {
            return ResponseEntity.badRequest().body("Please log in with your credential.");
        }

        var userDetails = (CustomUserDetails) auth.getPrincipal();
        var username = auth.getName();
        var roles = auth.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority).toList();
        var shopId = userDetails.getShopIds();
        Map<String, Object> data = new TreeMap<>();
        data.put("name", username);
        data.put("role", roles);
        data.put("shop", shopId);
        return ResponseEntity.ok().body(data);
    }
}
