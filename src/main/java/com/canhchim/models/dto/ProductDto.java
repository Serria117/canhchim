package com.canhchim.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto implements Serializable {
    private Long id;
    private Long categoryId;
    private String categoryName;
    private String productName;
    private Long productPrice;
    private Long shopId;
    private String shopName;
    private Set<String> imageUrl = new HashSet<>();
}
