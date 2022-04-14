package com.canhchim.models.dto;

import com.canhchim.models.PrdProduct;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto implements Serializable
{
    private Long id;
    private String productName;
    private String productCode;
    private Long productPrice;
    private Long productPrice2;
    private Long productPrice3;
    private Long categoryId;
    private String categoryName;

    private Long inputUser;
    private Long shopId;
    private String shopName;
    private Set<String> imageUrl = new HashSet<>();
    private int isTopUp;
    private int hasTopUp;

    private int isMultiPrice;
    public ProductDto (PrdProduct pModel)
    {
        this.id = pModel.getId();
        this.productName = pModel.getProductName();
        this.productCode = pModel.getProductCode();
        this.categoryId = pModel.getPrdCategories().getId();
        this.categoryName = pModel.getPrdCategories().getCategoryName();
        this.productPrice = pModel.getProductPrice();
        this.productPrice2 = pModel.getProductPrice2();
        this.productPrice3 = pModel.getProductPrice3();
        this.shopId = pModel.getShpShop().getId();
        this.isTopUp = pModel.getIsTopUp();
        this.hasTopUp = pModel.getHasTopUp();
        this.isMultiPrice = pModel.getIsMultiPrice();
        this.shopName = pModel.getShpShop().getShopName();
        this.imageUrl = pModel.getPrdImages().stream()
                              .filter(img -> !img.getImageUrl().equals("0"))
                              .map(prdImage -> ServletUriComponentsBuilder
                                      .fromCurrentContextPath()
                                      .pathSegment("images")
                                      .pathSegment(prdImage.getImageUrl())
                                      .toUriString())
                              .collect(Collectors.toSet());
    }
}
