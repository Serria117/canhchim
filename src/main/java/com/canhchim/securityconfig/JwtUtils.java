package com.canhchim.securityconfig;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class JwtUtils
{
    @Value("${jwt.secret}")
    private String key;
    @Value("${jwt.expiration}")
    private long expiration;

    //region Generate Token
    private String createToken(Map<String, Object> claims, String subject)
    {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
    }

    public String generateToken(UserDetails userDetails)
    {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }
//endregion

    private Claims extraction(String token)
    {
        return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
    }

    public <T> T extractClaims(String token, Function<Claims, T> claimResolver)
    {
        final Claims claims = extraction(token);
        return claimResolver.apply(claims);
    }

    public String getUsername(String token)
    {
        return extractClaims(token, Claims::getSubject);
    }

    public Date getExpiration(String token)
    {
        return extractClaims(token, Claims::getExpiration);
    }

    public boolean validate(String token, UserDetails userDetails)
    {
        String username = getUsername(token);
        boolean isExpired = getExpiration(token).before(new Date());

        return (username.equals(userDetails.getUsername()) && !isExpired);
    }
}
