package com.canhchim.models.dto;

import com.canhchim.models.PrdImage;
import com.canhchim.models.PrdProduct;
import com.canhchim.models.ShpUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

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
    private ShpUser inputUser;
    private String shopName;
    private Set<String> imageUrl = new HashSet<>();

    public ProductDto(PrdProduct p) {
        this.id = p.getId();
        this.categoryId = p.getPrdCategories().getId();
        this.categoryName = p.getPrdCategories().getCategoryName();
        this.productPrice = p.getProductPrice();
        this.shopId = p.getShpShop().getId();
        this.shopName = p.getShpShop().getShopName();
        this.imageUrl = p.getPrdImages().stream().map(PrdImage::getImageUrl).collect(Collectors.toSet());
    }

}
