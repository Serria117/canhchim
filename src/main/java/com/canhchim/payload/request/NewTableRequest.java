package com.canhchim.payload.request;

import com.canhchim.models.ShpTable;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
@Getter @Setter @Data
public class NewTableRequest implements Serializable
{
    Long Id;
    @NotNull
    Long shopId;
    @NotNull
    Long zoneId;
    @NotNull @NotEmpty
    String tableName;
    Integer numberOfSeat;

}
