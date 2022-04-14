package com.canhchim.models.dto;

import com.canhchim.models.PrdCategory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Getter
@Setter
@NoArgsConstructor
public class CategoryDto
{
    long id;
    long shopId;
    String shopName;
    String categoryName;
    String imageUrl;

    public CategoryDto (PrdCategory category)
    {
        id = category.getId();
        shopId = category.getShop().getId();
        shopName = category.getShop().getShopName();
        categoryName = category.getCategoryName();
        if ( category.getCategoryImage() != null && category.getCategoryImage().equals("")) {
            imageUrl = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/images/")
                    .path(category.getCategoryImage())
                    .toUriString();
        }
        else {
            imageUrl = "";
        }
    }
}
