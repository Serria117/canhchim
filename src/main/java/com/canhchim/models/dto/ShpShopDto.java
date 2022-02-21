package com.canhchim.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ShpShopDto implements Serializable {
    private Long id;
    private String shopName;
    private String fullAddress;
    private Set<ProductDto> prdProducts = new LinkedHashSet<>();
}
