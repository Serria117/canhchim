package com.canhchim.securityconfig;

import com.canhchim.services.CustomerService;
import com.canhchim.services.UserService;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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
import java.util.Collection;

@Component
public class RequestFilter extends OncePerRequestFilter
{
    CustomerService customerService;
    UserService userService;
    JwtUtils jwtUtils;

    public RequestFilter(CustomerService customerService, UserService userService, JwtUtils jwtUtils)
    {
        this.customerService = customerService;
        this.userService = userService;
        this.jwtUtils = jwtUtils;
    }

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
        Collection<String> actionList = null;
        UserDetails loginUser;
        if ( headerAuthorization != null && headerAuthorization.startsWith("Bearer ") ) {
            jwt = headerAuthorization.substring(7);
            username = jwtUtils.getUsername(jwt);
            actionList = jwtUtils.getActionList(jwt);
        }

        if ( username != null && SecurityContextHolder.getContext().getAuthentication() == null ) {

            loginUser = userService.loadUserByUsername(username);
//            var authorities = loginUser.getAuthorities().stream()
//                                       .map(GrantedAuthority::getAuthority)
//                                       .toList();

            if ( jwtUtils.validate(jwt, loginUser) && filterAction(request, actionList) ) {
                var authToken = new UsernamePasswordAuthenticationToken(
                        loginUser,
                        null,
                        loginUser.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }

    private boolean filterAction(HttpServletRequest request, Collection<String> actionList)
    {
        //Allow access without authentication
        String[] allowAll = {
                "shop-auth-login",
                "shop-product-search"
        };
        String[] uri = request.getRequestURI().split("/");
        String action = uri[1] + "-" + uri[2];
        return actionList.contains(action) || Arrays.asList(allowAll).contains(action);
    }
}
