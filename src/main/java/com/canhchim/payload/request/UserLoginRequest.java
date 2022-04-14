package com.canhchim.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class UserLoginRequest implements Serializable
{
    private String username;
    private String password;
}
