package com.canhchim.securityconfig;

import com.canhchim.services.CustomerService;
import com.canhchim.services.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
    /**
     * <p>Path that will get access by public (no authentication required)</p>
     * <p>Use for testing new API</p>
     */
    private final String[] allowPublic = {
            "/images/**",
            "/shop-auth/login"
    };
    CustomerService customerService;
    UserService userService;
    RequestFilter requestFilter;

    public SecurityConfig(CustomerService customerService,
                          UserService userService,
                          RequestFilter requestFilter)
    {
        this.customerService = customerService;
        this.userService = userService;
        this.requestFilter = requestFilter;
    }

    @Bean
    public PasswordEncoder appPwEncode()
    {
        return new BCryptPasswordEncoder();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception
    {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {

        http.csrf().disable().cors().disable()
            .authorizeRequests()
            .antMatchers(allowPublic).permitAll()
            .anyRequest().authenticated()
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .addFilterBefore(requestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception
    {
        auth.userDetailsService(customerService).passwordEncoder(appPwEncode());
        auth.userDetailsService(userService).passwordEncoder(appPwEncode());
    }
}
