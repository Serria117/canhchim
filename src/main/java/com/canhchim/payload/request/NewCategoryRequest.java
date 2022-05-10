package com.canhchim.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor @NoArgsConstructor
public class NewCategoryRequest implements Serializable
{
    Long id = null;
    @NotNull
    Long shopId;
    @NotNull
    Long groupId;
    @NotNull @NotEmpty
    String categoryName;
    String description = "";
    String code = String.valueOf(System.currentTimeMillis());
    String image = null;
}
