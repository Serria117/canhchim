package com.canhchim.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;

@AllArgsConstructor
@NoArgsConstructor @Getter @Setter
public class UserRegisterRequest
{
    @NotNull
    private String fullName;
    @NotNull
    @Digits(integer = 12, fraction = 0)
    private String citizenIdentity;
    @NotNull
    private String username;
    @NotNull
    @Size(min = 6, max = 18)
    private String password;
    private String password2;
    @NotNull
    @Digits(integer = 12, fraction = 0)
    private String phoneNumber;
    @NotNull
    private long shopId;
    private Long[] roleId;
    @Min(0) @Max(1)
    private int isOwner;

 }
