package com.canhchim.payload.request;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class NewZoneRequest implements Serializable
{
    @NotNull
    Long shopId;
    @NotNull @NotEmpty (message = "Zone name must not be empty.")
    String zoneName;
}
