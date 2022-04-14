package com.canhchim.securityconfig;

import com.canhchim.services.CustomerService;
import com.canhchim.services.UserService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public class JwtRequestFilter extends OncePerRequestFilter
{
    @Autowired
    CustomerService customerService;
    @Autowired
    UserService userService;
    @Autowired
    JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            @NotNull HttpServletResponse response,
            @NotNull FilterChain filterChain)
            throws ServletException, IOException
    {
        final String headerAuthorization = request.getHeader("Authorization");
        String username = null;
        String jwt = null;
        UserDetails loginUser;
        if ( headerAuthorization != null && headerAuthorization.startsWith("Bearer ") ) {
            jwt = headerAuthorization.substring(7);
            username = jwtUtils.getUsername(jwt);
        }

        if ( username != null && SecurityContextHolder.getContext().getAuthentication() == null ) {

            loginUser = userService.loadUserByUsername(username);
            var authorities = loginUser.getAuthorities().stream()
                                       .map(GrantedAuthority::getAuthority)
                                       .toList();

            if ( jwtUtils.validate(jwt, loginUser) && filterAction(request, authorities) ) {
                var authToken = new UsernamePasswordAuthenticationToken(
                        loginUser,
                        null,
                        loginUser.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
            //if the login account does not pass validation of User authentication, move to the next validation for Customer
            else {
                loginUser = customerService.loadUserByUsername(username);
                if ( jwtUtils.validate(jwt, loginUser) ) {
                    var autToken = new UsernamePasswordAuthenticationToken(
                            loginUser,
                            null,
                            loginUser.getAuthorities()
                    );
                    autToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(autToken);
                }
            }
        }

        filterChain.doFilter(request, response);
    }

    private boolean filterAction(HttpServletRequest request, List<String> authorities)
    {
        //Allow access without needed permission
        String[] allowAll = {
                "shop-auth-login",
                "shop-product-search"
        };
        String[] uri = request.getRequestURI().split("/");
        String action = uri[1] + "-" + uri[2];
        System.out.println("Action = " + action);
        return authorities.contains(action) || Arrays.asList(allowAll).contains(action);
    }
}
