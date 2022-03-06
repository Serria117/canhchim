package com.canhchim.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.*;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CustomerRegisterDTO implements Serializable
{
    @NotNull
    @NotEmpty
    private String username;
    @Size(min = 6, max = 30)
    private String password;
    @Digits(integer = 12, fraction = 0)
    private String phone;
    @Email
    private String mainAddress;
    private String deliveryAddress;
}
