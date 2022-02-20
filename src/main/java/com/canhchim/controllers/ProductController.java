package com.canhchim.controllers;

import com.canhchim.models.PrdImage;
import com.canhchim.models.PrdProduct;
import com.canhchim.models.dto.ProductDto;
import com.canhchim.repositories.PrdProductRepository;
import com.canhchim.response.ResponseObject;
import com.canhchim.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("all")
    public ResponseEntity<ResponseObject> getAllProduct(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "itemPerPage", defaultValue = "12") int itemPerPage) {
        Page<PrdProduct> result = productService.findAll(page, itemPerPage);

        return result.isEmpty() ?
                ResponseEntity.noContent().build() :
                ResponseEntity.ok().body(new ResponseObject(
                        200,
                        "OK",
                        result.map(this::convertToProductDto)
                ));
    }

    private ProductDto convertToProductDto(PrdProduct p){
        return new ProductDto(
                p.getId(),
                p.getPrdCategories().getId(),
                p.getPrdCategories().getCategoryName(),
                p.getProductName(),
                p.getProductPrice(),
                p.getShpShop().getId(),
                p.getShpShop().getShopName(),
                p.getPrdImages().stream().map(PrdImage::getImageUrl).collect(Collectors.toSet())
        );
    }
}
